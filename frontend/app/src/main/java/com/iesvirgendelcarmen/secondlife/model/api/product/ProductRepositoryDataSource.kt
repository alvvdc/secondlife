package com.iesvirgendelcarmen.secondlife.model.api.product

import com.iesvirgendelcarmen.secondlife.model.Product

interface ProductRepositoryDataSource {
    fun getUnsoldProducts(callback :ProductRepositoryCallback.ListProducts)
    fun getUnsoldProductsByCategory(category: String, callback: ProductRepositoryCallback.ListProducts)
    fun postNewProduct(product :Product, callback: ProductRepositoryCallback.OneProduct)
    fun visitProduct(productId :String, callback: ProductRepositoryCallback.VisitProduct)
    fun editProduct(product: Product, callback: ProductRepositoryCallback.EditProduct)
}