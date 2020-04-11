package com.android.trendingcermati.dependencies.module

import com.android.trendingcermati.R
import com.android.trendingcermati.helper.LiveDataCallAdapterFactory
import com.android.trendingcermati.helper.NetworkServiceFactory
import com.android.trendingcermati.helper.RequestInterceptor
import com.android.trendingcermati.network.SearchService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single { NetworkServiceFactory.makeClientService(get(), get(), get()) }

    single { NetworkServiceFactory.makeLoggingInterceptor() }

    single { NetworkServiceFactory.makeCache(get()) }

    single { NetworkServiceFactory.makeGson() }

    single { RequestInterceptor() }

    single(named("RetrofitUser")) {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(get<OkHttpClient>(named("general_client")))
            .baseUrl(androidContext().getString(R.string.server_url) + "/")
            .build()
    }
    single { get<Retrofit>(named("RetrofitUser")).create(SearchService::class.java) }
}