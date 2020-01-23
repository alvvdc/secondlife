package com.iesvirgendelcarmen.secondlife.model.api

import com.iesvirgendelcarmen.secondlife.model.Product

interface ProductRepositoryDataSource {
    fun getAllProducts(callback :ProductRepositoryCallback.ListProducts)
    fun editProduct(product: Product, callback: ProductRepositoryCallback.EditProduct)
}