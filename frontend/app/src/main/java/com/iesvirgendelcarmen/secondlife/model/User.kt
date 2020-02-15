package com.iesvirgendelcarmen.secondlife.model

data class User (
    val id: String,
    val nickname: String,
    val name: String,
    val lastName1: String,
    val lastName2: String,
    val email: String,
    val password: String,
    val phone: String,
    val type: Int,
    val image: String
)

data class UserWithoutId (
    val nickname: String,
    val name: String,
    val lastName1: String,
    val lastName2: String,
    val email: String,
    val password: String,
    val phone: String,
    val type: Int,
    val image: String
)




