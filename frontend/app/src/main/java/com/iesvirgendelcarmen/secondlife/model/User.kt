package com.iesvirgendelcarmen.secondlife.model

data class User (
    val id: Int,
    val nickname: String,
    val name: String,
    val lastName1: String,
    val lastName2: String,
    val email: String,
    val password: String,
    val phone: String,
    val type: Int
)

data class UserWithoutId (
    val nickname: String,
    val name: String,
    val lastName1: String,
    val lastName2: String,
    val email: String,
    val password: String,
    val phone: String,
    val type: Int
)




