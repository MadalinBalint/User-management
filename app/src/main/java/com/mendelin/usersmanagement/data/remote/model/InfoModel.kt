package com.mendelin.usersmanagement.data.remote.model

import androidx.annotation.Keep

@Keep
data class InfoModel(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String,
)