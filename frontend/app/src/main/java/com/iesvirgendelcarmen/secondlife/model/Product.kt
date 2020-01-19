package com.iesvirgendelcarmen.secondlife.model

data class Product (var title :String, var description :String, var price :Float, val images :MutableList<String>)