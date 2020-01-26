package com.iesvirgendelcarmen.secondlife.model.api.product

import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryCallback
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductRepositoryRetrofit :
    UserRepositoryDataSource {

    private lateinit var api : ProductApi

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(APIConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(
            ProductApi::class.java)
    }

    override fun getAllProducts(callback : UserRepositoryCallback.ListProducts) {
        callback.onLoading()

        val call = api.getAllProducts()
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


}