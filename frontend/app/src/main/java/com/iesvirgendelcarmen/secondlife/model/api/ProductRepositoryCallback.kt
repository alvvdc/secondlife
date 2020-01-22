package com.iesvirgendelcarmen.secondlife.model.api

import com.iesvirgendelcarmen.secondlife.model.Product

interface ProductRepositoryCallback {
    interface ListProducts {
        fun onResponse(products :List<Product>)
        fun onError(message :String)
        fun onLoading()
    }
}