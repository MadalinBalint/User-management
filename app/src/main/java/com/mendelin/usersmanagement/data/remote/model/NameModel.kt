package com.mendelin.usersmanagement.data.remote.model

import androidx.annotation.Keep

@Keep
data class NameModel(
    val title: String,
    val first: String,
    val last: String
)