package com.iesvirgendelcarmen.secondlife.model

data class User (
    var id: Int,
    var nickname: String,
    var name: String,
    var lastName1: String,
    var lastName2: String,
    var email: String,
    var password: String,
    var phone: String,
    var type: Int
)
