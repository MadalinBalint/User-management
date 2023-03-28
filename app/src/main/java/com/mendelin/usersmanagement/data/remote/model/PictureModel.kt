package com.mendelin.usersmanagement.data.remote.model

import androidx.annotation.Keep

@Keep
data class PictureModel(
    val large: String,
    val medium: String,
    val thumbnail: String,
)