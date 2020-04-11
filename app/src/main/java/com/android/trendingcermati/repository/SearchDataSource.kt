package com.android.trendingcermati.repository

import androidx.lifecycle.LiveData
import androidx.paging.PageKeyedDataSource
import com.android.trendingcermati.entity.User
import com.android.trendingcermati.entity.UserResult
import com.android.trendingcermati.helper.SourceStatus

class SearchDataSource(private var repository: SearchRepository) :
    PageKeyedDataSource<Long, User>() {

    var query = ""

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, User>
    ) {
        when (query.isNotBlank()) {
            true -> repository.getSearchUser(
                query = query,
                page = 1,
                initialCallParams = Pair(callback, params)
            )
        }
        repository.resourceLiveData?.value
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, User>) {
        when (query.isNotBlank() && !repository.incompleteResults) {
            true -> repository.getSearchUser(
                query = query,
                page = params.key,
                callParams = Pair(callback, params)
            )
        }
        repository.resourceLiveData?.value
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, User>) {}

    fun resources(): LiveData<SourceStatus<UserResult>> {
        return repository.resource
    }
}