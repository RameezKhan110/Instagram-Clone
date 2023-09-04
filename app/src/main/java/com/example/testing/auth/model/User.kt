package com.example.testing.auth.model

data class User(
    val userImage: String,
    val userName: String,
    val userEmail: String,
    val userPassword: String,
    val userId: String?,

) {
    constructor(): this("","","","","")
}
