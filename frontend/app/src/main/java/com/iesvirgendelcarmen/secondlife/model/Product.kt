package com.iesvirgendelcarmen.secondlife.model

data class Product (
    var id: String,
    var publisher: String,
    var title :String,
    var description :String,
    var price :Float,
    val images :MutableList<String>,
    val isSold :Boolean = false)