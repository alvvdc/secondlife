package com.iesvirgendelcarmen.secondlife.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product (
    var _id: String,
    var publisher: String,
    var title :String,
    var description :String,
    var price :Float,
    val images :MutableList<String>,
    val category: Category,
    val isSold :Boolean = false) :Parcelable