package com.iesvirgendelcarmen.secondlife.model

data class Product (
    var _id: String,
    var publisher: String,
    var title :String,
    var description :String,
    var price :Float,
    val images :MutableList<String>,
    val category: Category,
    val isSold :Boolean = false)