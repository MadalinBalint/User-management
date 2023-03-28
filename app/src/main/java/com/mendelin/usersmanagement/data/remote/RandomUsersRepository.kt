package com.mendelin.usersmanagement.data.remote

import com.mendelin.usersmanagement.data.remote.model.RandomUserResponse
import retrofit2.Response
import javax.inject.Inject

class RandomUsersRepository @Inject constructor(private val api: RandomUsersApi) {
    suspend fun getRandomUsersList(page: Int, results: Int, seed: String): Response<RandomUserResponse> =
        api.getRandomUsersList(page, results, seed)
}