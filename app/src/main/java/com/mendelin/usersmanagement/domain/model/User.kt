package com.mendelin.usersmanagement.domain.model

import androidx.annotation.Keep

@Keep
data class User(
    val imageUrl: String,
    val name: String,
    val age: Int,
    val nationality: String,
    val timestamp: String,
    val hasAttachement: Boolean = false,
    val isFavorite: Boolean = false,
)
