package com.android.trendingcermati.ui.home

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.trendingcermati.entity.User
import com.android.trendingcermati.entity.UserResult
import com.android.trendingcermati.helper.AppExecutors
import com.android.trendingcermati.helper.SourceStatus
import com.android.trendingcermati.network.Param
import com.android.trendingcermati.repository.SearchDataSource
import com.android.trendingcermati.repository.SearchDataSourceFactory

class UserViewModel(
    private val factory: SearchDataSourceFactory,
    private val appExecutors: AppExecutors
) : ViewModel(), LifecycleOwner {

    override fun getLifecycle(): Lifecycle = LifecycleRegistry(this)

    private val config = PagedList.Config.Builder().apply {
        setEnablePlaceholders(true)
        setPageSize(Param.LIMIT_ITEM)
    }.build()

    val resources: LiveData<SourceStatus<UserResult>> = Transformations
        .switchMap<SearchDataSource, SourceStatus<UserResult>>(
            factory.main,
            SearchDataSource::resources
        )

    val paged: LiveData<PagedList<User>>
        get() = LivePagedListBuilder<Long, User>(
            factory,
            config
        ).apply {
            setFetchExecutor(appExecutors.mainThread())
        }.build()

    fun searchUsers(query: String) {
        factory.apply {
            user = query
            dataStore?.invalidate()
        }
    }

}