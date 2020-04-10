package com.android.trendingcermati.base

import android.os.Bundle
import com.android.trendingcermati.helper.SourceStatus
import com.android.trendingcermati.helper.Status

interface BaseView {

    fun getLayoutResId(): Int

    fun initView(savedInstanceState: Bundle?)

    fun initEvent()

    fun loadingData(isFromSwipe: Boolean = false)

    fun observeData()

    /**
     * Group function show and gone UI view progress bar
     */

    fun startLoading()

    fun stopLoading()

    /**
     * Group function show and gone state view
     */

    fun onInternetError()

    fun onError(throwable: Throwable? = null)


    fun <T> parseObserveData(
        resource: SourceStatus<T>,
        resultLoading: (T?) -> Unit = { startLoading() },
        resultSuccess: (T, T) -> Unit = { _, _ -> },
        resultNetworkFailed: (Throwable?) -> Unit = { onInternetError() },
        resultError: (Throwable?) -> Unit = { onError(it) }
    ) {
        when (resource.status) {
            Status.LOADING -> {
                resultLoading(resource.data)
            }
            Status.SUCCESS -> {
                stopLoading()
                resource.data?.let { resultSuccess(it, it) }
            }
            Status.NETWORK_FAILED -> {
                stopLoading()
                resultNetworkFailed(resource.throwable)
            }
            Status.ERROR -> {
                stopLoading()
                resultError(resource.throwable)
            }
            else -> {
                stopLoading()
                resultError(resource.throwable)
            }
        }
    }
}