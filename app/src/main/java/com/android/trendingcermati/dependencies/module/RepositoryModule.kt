package com.android.trendingcermati.dependencies.module

import com.android.trendingcermati.repository.SearchDataSourceFactory
import com.android.trendingcermati.repository.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { SearchRepository(get(), get()) }

    single { SearchDataSourceFactory(get()) }
}