package com.android.trendingcermati.helper

//enum class StatusState {
//    LOADING,
//    LOADING_PAGE,
//    SUCCESS,
//    NOT_FOUND,
//    UNAUTHORIZATION,
//    LIMIT_EXCEEDED,
//    NETWORK_FAILED,
//    ERROR
//}

sealed class StatusState {
    object Loading : StatusState()
    object LoadingPage : StatusState()
    object Success : StatusState()
    object NotFound : StatusState()
    object Unauthorized : StatusState()
    object LimitExceeded : StatusState()
    object NetworkFailed : StatusState()
    object UnknownError : StatusState()
}