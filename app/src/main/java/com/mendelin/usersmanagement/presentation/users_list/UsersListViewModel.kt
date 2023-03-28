package com.mendelin.usersmanagement.presentation.users_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mendelin.usersmanagement.domain.model.User
import com.mendelin.usersmanagement.domain.usecase.RandomUsersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val useCase: RandomUsersListUseCase,
) : ViewModel() {
    private val _userState = MutableStateFlow<PagingData<User>>(PagingData.empty())
    val usersListState = _userState.asStateFlow()

    private val _viewState = MutableStateFlow(UserUiState())
    val viewState = _viewState.asStateFlow()

    private val pagingData = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = 3),
        pagingSourceFactory = {
            UsersListPagingSource(useCase, object : PageStatusCallback {
                override fun onPageLoading() {
                    _viewState.update {
                        it.copy(
                            isLoading = true,
                            isFailed = Pair(false, ""),
                        )
                    }
                }

                override fun onPageSuccess(page: Int) {
                    Timber.i("Page #$page loaded successfully")
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            isFailed = Pair(false, ""),
                        )
                    }
                }

                override fun onPageError(message: String) {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            isFailed = Pair(true, message),
                        )
                    }
                }
            })
        }
    ).flow.cachedIn(viewModelScope)

    fun fetchRandomUsersList(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            pagingData.flowOn(dispatcher)
                .collectLatest { data ->
                    _userState.value = data
                }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}