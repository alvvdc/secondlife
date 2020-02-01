package com.iesvirgendelcarmen.secondlife.model.api.product

import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.ProductDto
import retrofit2.Call
import retrofit2.http.*

interface ProductApi {
    @GET(APIConfig.PRODUCT_ROUTE)
    fun getUnsoldProducts() :Call<List<Product>>

    @GET("${APIConfig.PRODUCT_ROUTE}/{category}")
    fun getUnsoldProductsByCategory(@Path("category") category :String) :Call<List<Product>>

    @POST(APIConfig.PRODUCT_ROUTE)
    fun postNewProduct(@Body product : ProductDto) :Call<Product>

    @FormUrlEncoded
    @PUT("${APIConfig.PRODUCT_ROUTE}/{id}")
    fun editProduct(@Path("id") id: String,
                    @Field("publisher") publisher: String,
                    @Field("title") title: String,
                    @Field("description") description: String,
                    @Field("price") price: Float,
                    @Field("images") images: MutableList<String>) : Call<Product>

}