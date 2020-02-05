package com.iesvirgendelcarmen.secondlife.model.api.product

import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductRepositoryRetrofit : ProductRepositoryDataSource {

    private lateinit var api : ProductApi

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("${APIConfig.BASE_URL}/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(
            ProductApi::class.java)
    }

    override fun getUnsoldProducts(callback :ProductRepositoryCallback.ListProducts) {
        callback.onLoading()

        val call = api.getUnsoldProducts()
        call.enqueue(object : Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val responseProducts = response.body().orEmpty()
                callback.onResponse(responseProducts)
            }

        })
    }

    override fun getUnsoldProductsByCategory(category: String, callback: ProductRepositoryCallback.ListProducts) {
        callback.onLoading()

        val call = api.getUnsoldProductsByCategory(category)
        call.enqueue(object : Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val responseProducts = response.body().orEmpty()
                callback.onResponse(responseProducts)
            }

        })
    }

    override fun editProduct(product: Product, callback: ProductRepositoryCallback.EditProduct) {
        callback.onLoading()

        val call = api.editProduct(
            product.id,
            product.publisher,
            product.title,
            product.description,
            product.price,
            product.images
        )

        call.enqueue(object : Callback<Product>{
            override fun onFailure(call: Call<Product>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                callback.onResponse(product)
            }

        })
    }
}