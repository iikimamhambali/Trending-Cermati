package com.android.trendingcermati.network

import androidx.lifecycle.LiveData
import com.android.trendingcermati.entity.UserResult
import com.android.trendingcermati.extention.ApiResponse
import com.android.trendingcermati.network.Param.LIMIT_ITEM
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/users")
    fun getUser(
        @Query("q") queryUser: String,
        @Query("page") page: Long,
        @Query("per_page") perPage: Int = LIMIT_ITEM
    ): LiveData<ApiResponse<UserResult>>
}