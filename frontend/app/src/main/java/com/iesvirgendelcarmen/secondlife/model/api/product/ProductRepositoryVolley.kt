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
import com.android.volley.toolbox.JsonObjectRequest
import com.iesvirgendelcarmen.secondlife.model.ProductVisits
import org.json.JSONObject


object ProductRepositoryVolley :ProductRepositoryDataSource {

    override fun getProductById(id: String, callback: ProductRepositoryCallback.OneProduct) {
        VolleySingleton.getInstance().requestQueue

        val GET_PRODUCT_URL = "${APIConfig.BASE_URL}/${APIConfig.PRODUCT_ROUTE}/${id}"

        val stringRequest = StringRequest (
            Request.Method.GET,
            GET_PRODUCT_URL,
            Listener { response ->

                val product = Gson().fromJson<Product>(response, Product::class.java)
                callback.onResponse(product)
            },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        )
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }

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


        val GET_PRODUCTS_URL = "${APIConfig.BASE_URL}/${APIConfig.PRODUCT_ROUTE}/category/$category"

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

    override fun postNewProduct(product: Product, token :String, callback: ProductRepositoryCallback.OneProduct) {
        VolleySingleton.getInstance().requestQueue

        val POST_PRODUCT_URL = "${APIConfig.BASE_URL}/${APIConfig.PRODUCT_ROUTE}"

        val json = Gson().toJson(product, Product::class.java)
        val jsonObject = JSONObject(json)

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            POST_PRODUCT_URL,
            jsonObject,

            Listener<JSONObject> {
                val product = Gson().fromJson(it.toString(), Product::class.java)
                callback.onResponse(product)
            },
            Response.ErrorListener {
                callback.onError(it.toString())
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf("x-access-token" to token)
            }
        }

        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest)
    }

    override fun updateProduct(product: Product, token :String, callback: ProductRepositoryCallback.OneProduct) {
        VolleySingleton.getInstance().requestQueue

        val PUT_PRODUCT_URL = "${APIConfig.BASE_URL}/${APIConfig.PRODUCT_ROUTE}/${product._id}"

        val json = Gson().toJson(product, Product::class.java)
        val jsonObject = JSONObject(json)

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.PUT,
            PUT_PRODUCT_URL,
            jsonObject,

            Listener<JSONObject> { response ->
                val product = Gson().fromJson(response.toString(), Product::class.java)
                callback.onResponse(product)
            },
            Response.ErrorListener { error ->
                callback.onError(error.toString())
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf("x-access-token" to token)
            }
        }

        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest)
    }

    override fun deleteProduct(product: Product, token :String, callback: ProductRepositoryCallback.OneProduct) {
        VolleySingleton.getInstance().requestQueue

        val GET_PRODUCT_URL = "${APIConfig.BASE_URL}/${APIConfig.PRODUCT_ROUTE}/${product._id}"

        val stringRequest = object : StringRequest (
            Method.DELETE,
            GET_PRODUCT_URL,
            Listener { response ->

                val product = Gson().fromJson<Product>(response, Product::class.java)
                callback.onResponse(product)
            },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf("x-access-token" to token)
            }
        }
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }

    override fun visitProduct(productId: String, callback: ProductRepositoryCallback.VisitProduct) {
        VolleySingleton.getInstance().requestQueue

        val VISIT_PRODUCT_URL = "${APIConfig.BASE_URL}/${APIConfig.PRODUCT_ROUTE}/$productId/visit"

        val stringRequest = StringRequest (
            Request.Method.GET,
            VISIT_PRODUCT_URL,
            Listener { response ->

                val productVisits = Gson().fromJson(response, ProductVisits::class.java)
                callback.onResponse(productVisits)
            },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        )
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }

    override fun getProductsByUser(id: String, callback: ProductRepositoryCallback.ListProducts) {
        callback.onLoading()

        VolleySingleton.getInstance().requestQueue

        val GET_PRODUCTS_URL = "${APIConfig.BASE_URL}/${APIConfig.USER_ROUTE}/$id/product"

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