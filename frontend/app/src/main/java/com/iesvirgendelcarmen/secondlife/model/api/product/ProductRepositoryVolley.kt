package com.iesvirgendelcarmen.secondlife.model.api.product

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.api.VolleySingleton
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryCallback
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryDataSource

object ProductRepositoryVolley :
    UserRepositoryDataSource {


    override fun getAllProducts(callback: UserRepositoryCallback.ListProducts) {
        callback.onLoading()

        VolleySingleton.getInstance().requestQueue


        val GET_PRODUCTS_URL = "${APIConfig.BASE_URL}/${APIConfig.PRODUCT_ROUTE}"

        val stringRequest = StringRequest (
            Request.Method.GET,
            GET_PRODUCTS_URL,
            Listener { response ->

                val listType = object : TypeToken<List<Product>>() {}.type
                val products = Gson().fromJson<List<Product>>(response, listType)

                callback.onResponse(products)
            },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        )
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }

}