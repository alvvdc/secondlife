package com.iesvirgendelcarmen.secondlife.model.api

import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET(APIConfig.PRODUCT_ROUTE)
    fun getUnsoldProducts() :Call<List<Product>>

    @GET("${APIConfig.PRODUCT_ROUTE}/{category}")
    fun getUnsoldProductsByCategory(@Path("category") category :String) :Call<List<Product>>
}