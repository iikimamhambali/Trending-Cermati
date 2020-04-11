package com.android.trendingcermati.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.PageKeyedDataSource
import com.android.trendingcermati.base.BaseRepositoryLiveData
import com.android.trendingcermati.entity.User
import com.android.trendingcermati.entity.UserResult
import com.android.trendingcermati.helper.ApiResponse
import com.android.trendingcermati.helper.ApiSuccessResponse
import com.android.trendingcermati.helper.AppExecutors
import com.android.trendingcermati.helper.SourceStatus
import com.android.trendingcermati.network.SearchService

class SearchRepository(
    private val appExtractors: AppExecutors,
    private val service: SearchService
) {

    val resource = MediatorLiveData<SourceStatus<UserResult>>()
    var incompleteResults = false
    var resourceLiveData: LiveData<SourceStatus<UserResult>>? = null

    fun getSearchUser(
        query: String,
        page: Long,
        initialCallParams: Pair<PageKeyedDataSource.LoadInitialCallback<Long, User>, PageKeyedDataSource.LoadInitialParams<Long>>? = null,
        callParams: Pair<PageKeyedDataSource.LoadCallback<Long, User>, PageKeyedDataSource.LoadParams<Long>>? = null
    ): LiveData<SourceStatus<UserResult>> {
        resourceLiveData = object : BaseRepositoryLiveData<UserResult>(appExtractors, page > 1) {

            override fun fetchData(response: ApiSuccessResponse<UserResult>) {
                incompleteResults = response.body.incompleteResults
                initialCallParams?.let {
                    it.first.onResult(
                        response.body.item,
                        null,
                        2
                    )
                }
                callParams?.let {
                    it.first.onResult(
                        response.body.item,
                        it.second.key.inc()
                    )
                }
            }

            override fun result(): MediatorLiveData<SourceStatus<UserResult>> = resource

            override fun loadFromNetwork(): LiveData<ApiResponse<UserResult>> {
                return service.getUser(query, page = page)
            }
        }.asLiveData()

        return resourceLiveData as LiveData<SourceStatus<UserResult>>
    }
}