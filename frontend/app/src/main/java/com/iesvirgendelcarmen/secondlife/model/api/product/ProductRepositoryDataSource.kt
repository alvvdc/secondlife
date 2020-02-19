package com.iesvirgendelcarmen.secondlife.model.api.product

import com.iesvirgendelcarmen.secondlife.model.Product

interface ProductRepositoryDataSource {
    fun getProductById(id :String, callback: ProductRepositoryCallback.OneProduct)
    fun getUnsoldProducts(callback :ProductRepositoryCallback.ListProducts)
    fun getUnsoldProductsByCategory(category: String, callback: ProductRepositoryCallback.ListProducts)
    fun postNewProduct(product :Product, token :String, callback: ProductRepositoryCallback.OneProduct)
    fun updateProduct(product: Product, token :String, callback: ProductRepositoryCallback.OneProduct)
    fun deleteProduct(product: Product, token :String, callback: ProductRepositoryCallback.OneProduct)
    fun visitProduct(productId :String, callback: ProductRepositoryCallback.VisitProduct)
}