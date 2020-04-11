package com.android.trendingcermati.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.android.trendingcermati.entity.User

class SearchDataSourceFactory(private var repository: SearchRepository) :
    DataSource.Factory<Long, User>(){

    var dataStore: SearchDataSource? = null
    val main: MutableLiveData<SearchDataSource> = MutableLiveData()
    var user: String = ""

    override fun create(): DataSource<Long, User> {
        dataStore = SearchDataSource(repository).apply { query = user }
        return dataStore.also { main.value = it }!!
    }
}