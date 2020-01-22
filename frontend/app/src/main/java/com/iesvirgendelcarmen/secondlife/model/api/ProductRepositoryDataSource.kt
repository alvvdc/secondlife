package com.iesvirgendelcarmen.secondlife.model.api

interface ProductRepositoryDataSource {
    fun getAllProducts(callback :ProductRepositoryCallback.ListProducts)
}