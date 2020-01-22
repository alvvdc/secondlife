package com.iesvirgendelcarmen.secondlife.model.api

import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {
    @GET(APIConfig.PRODUCT_ROUTE)
    fun getAllProducts() :Call<List<Product>>
}