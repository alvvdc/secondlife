package com.iesvirgendelcarmen.secondlife.model.api

import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductApi {
    @GET(APIConfig.PRODUCT_ROUTE)
    fun getAllProducts() :Call<List<Product>>

    @FormUrlEncoded
    @PUT("${APIConfig.PRODUCT_ROUTE}/{id}")
    fun editProduct(@Path("id") id: Int,
                    @Field("idUser") idUser: Int,
                    @Field("title") title: String,
                    @Field("description") description: String,
                    @Field("price") price: Float,
                    @Field("images") images: MutableList<String>) : Call<Product>
}