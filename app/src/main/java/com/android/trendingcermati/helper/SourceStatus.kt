package com.android.trendingcermati.helper

data class SourceStatus<out T>(
    val status: Status,
    val data: T?,
    val throwable: Throwable? = null
) {

    companion object {

        fun <T> loading(data: T? = null): SourceStatus<T> {
            return SourceStatus(Status.LOADING, data)
        }

        fun <T> loadingPage(data: T? = null): SourceStatus<T> {
            return SourceStatus(Status.LOADING_PAGE, data)
        }

        fun <T> success(data: T?): SourceStatus<T> {
            return SourceStatus(Status.SUCCESS, data, null)
        }

        fun <T> empty(throwable: Throwable? = null): SourceStatus<T> {
            return SourceStatus(Status.NOT_FOUND, null, throwable)
        }

        fun <T> unAuthorization(throwable: Throwable? = null): SourceStatus<T> {
            return SourceStatus(Status.UNAUTHORIZATION, null, throwable)
        }

        fun <T> limitExceeded(throwable: Throwable? = null): SourceStatus<T> {
            return SourceStatus(Status.LIMIT_EXCEEDED, null, throwable)
        }

        fun <T> networkFailed(throwable: Throwable? = null): SourceStatus<T> {
            return SourceStatus(Status.NETWORK_FAILED, null, throwable)
        }

        fun <T> error(throwable: Throwable? = null): SourceStatus<T> {
            return SourceStatus(Status.ERROR, null, throwable)
        }
    }
}