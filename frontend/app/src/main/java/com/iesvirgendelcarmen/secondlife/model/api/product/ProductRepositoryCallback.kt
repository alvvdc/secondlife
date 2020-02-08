package com.iesvirgendelcarmen.secondlife.model.api.product

import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.ProductVisits

interface ProductRepositoryCallback {
    interface ListProducts {
        fun onResponse(products :List<Product>)
        fun onError(message :String)
        fun onLoading()
    }

    interface OneProduct {
        fun onResponse(product: Product)
        fun onError(message :String)
    }

    interface VisitProduct {
        fun onResponse(productVisits :ProductVisits)
        fun onError(message :String)
    }

    interface EditProduct {
        fun onResponse(product: Product)
        fun onError(message :String)
        fun onLoading()
    }
}