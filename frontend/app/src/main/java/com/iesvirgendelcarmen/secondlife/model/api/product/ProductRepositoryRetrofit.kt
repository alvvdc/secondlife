package com.iesvirgendelcarmen.secondlife.model.api.product

import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.*
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

    override fun getProductById(id: String, callback: ProductRepositoryCallback.OneProduct) {
        val call = api.getProductById(id)

        call.enqueue(object : Callback<Product> {
            override fun onFailure(call: Call<Product>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                val responseProduct = response.body() ?: Product("", "", "", "", 0f, mutableListOf(), Category.OTROS)
                callback.onResponse(responseProduct)
            }
        })
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

    override fun postNewProduct(product: Product, callback: ProductRepositoryCallback.OneProduct) {
        val call = api.postNewProduct(ProductMapper.transformObjectBoToDto(product))

        call.enqueue(object : Callback<Product> {
            override fun onFailure(call: Call<Product>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                val responseProduct = response.body() ?: Product("", "", "", "", 0f, mutableListOf(), Category.OTROS)
                callback.onResponse(responseProduct)
            }

        })
    }

    override fun updateProduct(product: Product, callback: ProductRepositoryCallback.OneProduct) {
        val call = api.updateProduct(product._id, product)

        call.enqueue(object : Callback<Product> {
            override fun onFailure(call: Call<Product>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                val responseProduct = response.body() ?: Product("", "", "", "", 0f, mutableListOf(), Category.OTROS)
                callback.onResponse(responseProduct)
            }

        })
    }

    override fun deleteProduct(product: Product, callback: ProductRepositoryCallback.OneProduct) {
        val call = api.deleteProductById(product._id)

        call.enqueue(object : Callback<Product> {
            override fun onFailure(call: Call<Product>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                val responseProduct = response.body() ?: Product("", "", "", "", 0f, mutableListOf(), Category.OTROS)
                callback.onResponse(responseProduct)
            }
        })
    }

    override fun visitProduct(productId: String, callback: ProductRepositoryCallback.VisitProduct) {
        val call = api.visitProduct(productId)

        call.enqueue(object : Callback<ProductVisits> {
            override fun onFailure(call: Call<ProductVisits>, t: Throwable) {
                callback.onError(t.message.toString())
            }

            override fun onResponse(call: Call<ProductVisits>, response: Response<ProductVisits>) {
                val responseProductVisits = response.body() ?: ProductVisits("", -1)
                callback.onResponse(responseProductVisits)
            }

        })
    }
}