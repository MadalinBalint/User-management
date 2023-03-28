package com.mendelin.usersmanagement.presentation.users_list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mendelin.usersmanagement.data.remote.model.UserModel
import com.mendelin.usersmanagement.data.remote.model.toUser
import com.mendelin.usersmanagement.domain.Resource
import com.mendelin.usersmanagement.domain.model.User
import com.mendelin.usersmanagement.domain.usecase.RandomUsersListUseCase
import com.mendelin.usersmanagement.presentation.users_list.UsersListViewModel.Companion.PAGE_SIZE
import kotlinx.coroutines.flow.lastOrNull
import javax.inject.Inject

interface PageStatusCallback {
    fun onPageLoading()
    fun onPageSuccess(page: Int)
    fun onPageError(message: String)
}

class UsersListPagingSource @Inject constructor(val useCase: RandomUsersListUseCase, private val callback: PageStatusCallback) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentPage = params.key ?: STARTING_PAGE_INDEX

            callback.onPageLoading()
            val response = useCase(currentPage, PAGE_SIZE, "abc").lastOrNull()

            if (response?.data != null) {
                when (response) {
                    is Resource.Success -> {
                        callback.onPageSuccess(currentPage)
                        LoadResult.Page(
                            data = response.data.results.map(UserModel::toUser),
                            prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage.minus(1),
                            nextKey = if (currentPage < ENDING_PAGE_INDEX) currentPage.plus(1) else null
                        )
                    }
                    is Resource.Error -> {
                        val msg = response.message ?: "Unknown error"
                        callback.onPageError(msg)
                        LoadResult.Error(Exception(msg))
                    }
                }
            } else {
                val msg = "Null body response"
                callback.onPageError(msg)
                LoadResult.Error(Exception(msg))
            }

        } catch (exception: Exception) {
            callback.onPageError(exception.localizedMessage ?: "An exception occurred")
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 0
        private const val ENDING_PAGE_INDEX = 2
    }
}