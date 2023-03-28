package com.mendelin.usersmanagement.data.remote.model

import androidx.annotation.Keep
import com.mendelin.usersmanagement.domain.Utils.toTimeFormat
import com.mendelin.usersmanagement.domain.model.User

@Keep
data class UserModel(
    val gender: String,
    val name: NameModel,
    val location: LocationModel,
    val email: String,
    val login: LoginModel,
    val dob: DobModel,
    val registered: RegisteredModel,
    val phone: String,
    val cell: String,
    val id: IdModel,
    val picture: PictureModel,
    val nat: String,
)

fun UserModel.toUser(): User {
    return User(
        picture.large,
        "${name.first} ${name.last}",
        dob.age,
        nat,
        registered.date.toTimeFormat(),
        gender == "female"
    )
}