package com.mendelin.usersmanagement.data.remote.model

import androidx.annotation.Keep

@Keep
data class CoordinatesModel(
    val latitude: String,
    val longitude: String,
)