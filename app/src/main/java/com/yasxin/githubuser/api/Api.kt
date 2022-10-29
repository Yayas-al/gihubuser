package com.yasxin.githubuser.api

import com.yasxin.githubuser.data.model.User
import com.yasxin.githubuser.data.model.UserDetailResponse
import com.yasxin.githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")

    fun  getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")

    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")

    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")

    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}