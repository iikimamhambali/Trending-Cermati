package com.android.trendingcermati

import com.android.trendingcermati.base.BaseApplication
import com.android.trendingcermati.dependencies.libraries
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : BaseApplication() {

    override fun initApplication() {
        startKoin {
            modules(libraries)
            androidContext(this@Application)
        }
    }
}