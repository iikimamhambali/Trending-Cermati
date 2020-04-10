package com.android.trendingcermati.base

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.android.trendingcermati.helper.*
import java.io.IOException

abstract class BaseRepositoryLiveData<Type>(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<SourceStatus<Type>>()

    init {
        result.value = SourceStatus.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromLocal()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetchFromNetwork(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(SourceStatus.success(newData))
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: SourceStatus<Type>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<Type>) {
        val apiResponse = loadFromNetwork()
        result.addSource(dbSource) { newData ->
            setValue(SourceStatus.loading(newData))
        }
        setValue(SourceStatus.loading())
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        val newResponse = processResponse(response)
                        saveFromNetwork(newResponse)
                        appExecutors.mainThread().execute {
                            result.addSource(apiResponse) {
                                setValue(SourceStatus.success(newResponse))
                            }
                        }
                    }
                }
                is ApiErrorResponse -> {
                    appExecutors.mainThread().execute {
                        when {
                            response.error is IOException -> setValue(
                                SourceStatus.networkFailed(
                                    response.error
                                )
                            )
                            else -> setValue(SourceStatus.error(response.error))
                        }
                    }
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<SourceStatus<Type>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<Type>) = response.body

    @WorkerThread
    protected abstract fun saveFromNetwork(item: Type)

    @MainThread
    protected abstract fun shouldFetchFromNetwork(data: Type?): Boolean

    @MainThread
    protected abstract fun loadFromLocal(): LiveData<Type>

    @MainThread
    protected abstract fun loadFromNetwork(): LiveData<ApiResponse<Type>>
}