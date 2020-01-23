package com.iesvirgendelcarmen.secondlife.model.api

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product

object ProductRepositoryVolley :ProductRepositoryDataSource {

    override fun getAllProducts(callback: ProductRepositoryCallback.ListProducts) {
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

    override fun editProduct(product: Product, callback: ProductRepositoryCallback.EditProduct) {
        callback.onLoading()

        VolleySingleton.getInstance().requestQueue

        val EDIT_PRODUCT_URL = "${APIConfig.BASE_URL}/${APIConfig.PRODUCT_ROUTE}/${product.id}"

        val stringRequest = StringRequest (
            Request.Method.PUT,
            EDIT_PRODUCT_URL,
            Listener { response ->
                val listType = object : TypeToken<Product>() {}.type
                val product = Gson().fromJson<Product>(response, listType)
                callback.onResponse(product)
            },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        )
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }


}