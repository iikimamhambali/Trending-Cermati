package com.android.trendingcermati.helper

data class SourceStatus<out T>(
    val status: StatusState,
    val data: T?,
    val throwable: Throwable? = null
) {

    companion object {

        fun <T> loading(data: T? = null): SourceStatus<T> {
            return SourceStatus(StatusState.Loading, data)
        }

        fun <T> loadingPage(data: T? = null): SourceStatus<T> {
            return SourceStatus(StatusState.LoadingPage, data)
        }

        fun <T> success(data: T?): SourceStatus<T> {
            return SourceStatus(StatusState.Success, data, null)
        }

        fun <T> empty(throwable: Throwable? = null): SourceStatus<T> {
            return SourceStatus(StatusState.NotFound, null, throwable)
        }

        fun <T> unAuthorization(throwable: Throwable? = null): SourceStatus<T> {
            return SourceStatus(StatusState.Unauthorized, null, throwable)
        }

        fun <T> limitExceeded(throwable: Throwable? = null): SourceStatus<T> {
            return SourceStatus(StatusState.LimitExceeded, null, throwable)
        }

        fun <T> networkFailed(throwable: Throwable? = null): SourceStatus<T> {
            return SourceStatus(StatusState.NetworkFailed, null, throwable)
        }

        fun <T> error(throwable: Throwable? = null): SourceStatus<T> {
            return SourceStatus(StatusState.UnknownError, null, throwable)
        }
    }
}