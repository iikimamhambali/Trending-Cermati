package com.android.trendingcermati.base

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.android.trendingcermati.helper.*
import java.io.IOException

abstract class BaseRepositoryLiveData<Type>(
    private val appExecutors: AppExecutors,
    private val isLoadingPage: Boolean
) {

    private val result = MediatorLiveData<SourceStatus<Type>>()

    init {
        setLoadingViewType()
        fetchFromNetwork()
    }

    @MainThread
    private fun setValue(newValue: SourceStatus<Type>) {
        if (result().value != newValue) {
            result().value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = loadFromNetwork()
        setValue(SourceStatus.loading())
        result().addSource(apiResponse) { response ->
            result().removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        val newResponse = processResponse(response)
                        appExecutors.mainThread().execute {
                            fetchData(response)
                            setValue(SourceStatus.success(newResponse))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    appExecutors.mainThread().execute {
                        when {
                            response.errorCode == ErrorCodeResponse.NOT_FOUND ->{
                                setValue(SourceStatus.empty(response.error))
                            }
                            response.errorCode == ErrorCodeResponse.UNAUTHORIZED ->{
                                setValue(SourceStatus.unAuthorization(response.error))
                            }
                            response.errorCode == ErrorCodeResponse.RATE_LIMIT_EXCEEDED ->{
                                setValue(SourceStatus.limitExceeded(response.error))
                            }
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

    private fun setLoadingViewType() {
        when (isLoadingPage) {
            false -> {
                setValue(SourceStatus.loading(null))
            }
            else -> {
                setValue(SourceStatus.loadingPage(null))
            }
        }
    }

    fun asLiveData() = result() as LiveData<SourceStatus<Type>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<Type>) = response.body

    @MainThread
    protected abstract fun fetchData(response: ApiSuccessResponse<Type>)

    @MainThread
    protected abstract fun result(): MediatorLiveData<SourceStatus<Type>>

    @MainThread
    protected abstract fun loadFromNetwork(): LiveData<ApiResponse<Type>>
}