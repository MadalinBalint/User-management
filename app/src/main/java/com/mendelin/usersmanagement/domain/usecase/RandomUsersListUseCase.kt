package com.mendelin.usersmanagement.domain.usecase

import com.mendelin.usersmanagement.data.remote.RandomUsersRepository
import com.mendelin.usersmanagement.domain.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RandomUsersListUseCase @Inject constructor(private val repository: RandomUsersRepository) {
    operator fun invoke(page: Int, results: Int, seed: String) = flow {
        try {
            val apiResponse = repository.getRandomUsersList(page, results, seed)
            if (apiResponse.isSuccessful) {
                val body = apiResponse.body()
                if (body != null) {
                    emit(Resource.Success(body))
                } else {
                    emit(Resource.Error(message = "Null body exception"))
                }
            } else {
                emit(Resource.Error(message = apiResponse.message()))
            }
        } catch (ex: Exception) {
            emit(Resource.Error(message = ex.localizedMessage ?: "Exception"))
        }
    }
}