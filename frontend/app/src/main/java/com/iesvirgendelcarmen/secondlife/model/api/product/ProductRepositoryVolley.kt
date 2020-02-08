package com.iesvirgendelcarmen.secondlife.model.api.product

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.api.VolleySingleton
import java.nio.charset.Charset
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.NetworkResponse



object ProductRepositoryVolley :ProductRepositoryDataSource {
    override fun getUnsoldProducts(callback: ProductRepositoryCallback.ListProducts) {
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

    override fun getUnsoldProductsByCategory(category: String, callback: ProductRepositoryCallback.ListProducts) {
        callback.onLoading()

        VolleySingleton.getInstance().requestQueue


        val GET_PRODUCTS_URL = "${APIConfig.BASE_URL}/${APIConfig.PRODUCT_ROUTE}/$category"

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

    override fun postNewProduct(product: Product, callback: ProductRepositoryCallback.OneProduct) {
        /*VolleySingleton.getInstance().requestQueue

        val POST_PRODUCT_URL = "${APIConfig.BASE_URL}/${APIConfig.PRODUCT_ROUTE}"

        val stringRequest = object : StringRequest (
            Request.Method.POST,
            POST_PRODUCT_URL,
            Listener { response ->

                val product = Gson().fromJson(response, Product::class.java)
                callback.onResponse(product)
            },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        ) {
            override fun getPostBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return Gson().toJson(product, Product::class.java).toByteArray(Charsets.UTF_8)
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                var responseString = ""
                if (response != null) {
                    responseString = response.statusCode.toString()
                }
                return Response.success(
                    responseString,
                    HttpHeaderParser.parseCacheHeaders(response!!)
                )
            }
        }

        VolleySingleton.getInstance().addToRequestQueue(stringRequest)*/
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