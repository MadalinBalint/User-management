package com.mendelin.usersmanagement.data.remote


import com.mendelin.usersmanagement.data.remote.model.RandomUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUsersApi {
    @GET("api")
    suspend fun getRandomUsersList(
        @Query("page") page: Int,
        @Query("results") results: Int,
        @Query("seed") seed: String,
    ): Response<RandomUserResponse>
}