package com.iesvirgendelcarmen.secondlife.model.api

import com.iesvirgendelcarmen.secondlife.model.Category

interface ProductRepositoryDataSource {
    fun getUnsoldProducts(callback :ProductRepositoryCallback.ListProducts)
    fun getUnsoldProductsByCategory(category: String, callback: ProductRepositoryCallback.ListProducts)
}