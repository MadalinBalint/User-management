package com.mendelin.usersmanagement.data.remote.model

import androidx.annotation.Keep

@Keep
data class LocationModel(
    val street: StreetModel,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: CoordinatesModel,
    val timezone: TimezoneModel,
)