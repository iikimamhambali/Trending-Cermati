package com.android.trendingcermati.base

import android.os.Bundle
import com.android.trendingcermati.helper.SourceStatus
import com.android.trendingcermati.helper.StatusState

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

    fun onDataNotFound()

    fun onLimitExceeded()

    fun onUnAuthorization()

    fun onError(throwable: Throwable? = null)

    fun <T> parseObserveData(
        resource: SourceStatus<T>,
        resultLoading: (T?) -> Unit = { startLoading() },
        resultLoadingPage: (T?) -> Unit = { startLoading() },
        resultSuccess: (T, T) -> Unit = { _, _ -> },
        resultNetworkFailed: (Throwable?) -> Unit = { onInternetError() },
        resultDataNotFound: (Throwable?) -> Unit = { onDataNotFound() },
        resultLimitExeeded: (Throwable?) -> Unit = { onLimitExceeded() },
        resultAuthorization: (Throwable?) -> Unit = { onUnAuthorization() },
        resultError: (Throwable?) -> Unit = { onError(it) }
    ) {
        when (resource.status) {
            StatusState.Loading -> {
                resultLoading(resource.data)
            }
            StatusState.LoadingPage -> {
                resultLoading(resource.data)
            }
            StatusState.Success -> {
                stopLoading()
                resource.data?.let { resultSuccess(it, it) }
            }
            StatusState.NetworkFailed -> {
                stopLoading()
                resultNetworkFailed(resource.throwable)
            }
            StatusState.NotFound -> {
                stopLoading()
                resultNetworkFailed(resource.throwable)
            }
            StatusState.LimitExceeded -> {
                stopLoading()
                resultLimitExeeded(resource.throwable)
            }
            StatusState.Unauthorized -> {
                stopLoading()
                resultAuthorization(resource.throwable)
            }
            StatusState.UnknownError -> {
                stopLoading()
                resultError(resource.throwable)
            }
        }
    }
}