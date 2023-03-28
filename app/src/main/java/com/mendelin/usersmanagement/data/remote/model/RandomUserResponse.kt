package com.mendelin.usersmanagement.data.remote.model

import androidx.annotation.Keep

@Keep
data class RandomUserResponse(
    val results: List<UserModel>,
    val info: InfoModel
)
