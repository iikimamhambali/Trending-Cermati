package com.android.trendingcermati.base

import android.os.StrictMode
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

abstract class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        initApplication()
        enableStrictModeIfDebugBuild()
    }

    abstract fun initApplication()

    private fun enableStrictModeIfDebugBuild() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build()
        )
    }
}