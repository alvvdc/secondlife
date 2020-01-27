package com.iesvirgendelcarmen.secondlife.model.api

import com.iesvirgendelcarmen.secondlife.model.Product

interface ProductRepositoryDataSource {
    fun getUnsoldProducts(callback :ProductRepositoryCallback.ListProducts)
    fun getUnsoldProductsByCategory(category: String, callback: ProductRepositoryCallback.ListProducts)
    fun editProduct(product: Product, callback: ProductRepositoryCallback.EditProduct)
}