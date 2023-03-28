package com.mendelin.usersmanagement.data.remote.model

import androidx.annotation.Keep

@Keep
data class LoginModel(
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String,
)