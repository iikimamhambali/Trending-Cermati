package com.android.trendingcermati.helper

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class AppExecutors(
    private val diskIO: Executor,
    private val mainThread: Executor
) {

    companion object {
        const val FIXED_THREAD_POOL = 4
    }

    constructor() : this(
        Executors.newFixedThreadPool(FIXED_THREAD_POOL),
        MainThreadExecutor()
    )

    fun diskIO(): Executor {
        return diskIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}