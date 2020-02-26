package com.iesvirgendelcarmen.secondlife.model.api.product

import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.ProductDto
import com.iesvirgendelcarmen.secondlife.model.ProductVisits
import retrofit2.Call
import retrofit2.http.*

interface ProductApi {
    @GET("${APIConfig.PRODUCT_ROUTE}/{id}")
    fun getProductById(@Path("id") id :String) :Call<Product>

    @GET(APIConfig.PRODUCT_ROUTE)
    fun getUnsoldProducts() :Call<List<Product>>

    @GET("${APIConfig.PRODUCT_ROUTE}/category/{category}")
    fun getUnsoldProductsByCategory(@Path("category") category :String) :Call<List<Product>>

    @POST(APIConfig.PRODUCT_ROUTE)
    fun postNewProduct(@Body product : ProductDto, @Header("x-access-token") token :String) :Call<Product>

    @PUT("${APIConfig.PRODUCT_ROUTE}/{id}")
    fun updateProduct(@Path("id") id :String, @Body product :Product, @Header("x-access-token") token :String) :Call<Product>

    @DELETE("${APIConfig.PRODUCT_ROUTE}/{id}")
    fun deleteProductById(@Path("id") id :String, @Header("x-access-token") token :String) :Call<Product>

    @GET("${APIConfig.PRODUCT_ROUTE}/{id}/visit")
    fun visitProduct(@Path("id") productId :String) :Call<ProductVisits>

    @GET("${APIConfig.USER_ROUTE}/{id}/product")
    fun getProductsByUser(@Path("id") id :String) :Call<List<Product>>
}