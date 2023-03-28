package com.mendelin.usersmanagement.presentation.users_list

import androidx.annotation.Keep

@Keep
data class UserUiState(
    var isLoading: Boolean = false,
    var isFailed: Pair<Boolean, String> = Pair(false, ""),
)
