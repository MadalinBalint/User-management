package com.mendelin.usersmanagement.data.remote.model

import androidx.annotation.Keep

@Keep
data class TimezoneModel(
    val offset: String,
    val description: String,
)