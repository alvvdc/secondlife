package com.iesvirgendelcarmen.secondlife.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iesvirgendelcarmen.secondlife.model.api.*
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryCallback
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryDataSource
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryRetrofit
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryVolley

class ProductViewModel : ViewModel() {

    //val productsList = mutableListOf<Product>()
    val productListLiveData = MutableLiveData<Resource<List<Product>>>()
    private val productRepository : ProductRepositoryDataSource = ProductRepositoryRetrofit

    init {
        //mock()
    }

    /*private fun mock() {
        productsList.add(Product("1", "1","Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product("1", "1", "Mueble rústico", "", 232f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product("1", "1", "Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product("1", "1", "Mueble rústico", "", 232f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product("1", "1", "Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product("1", "1", "Mueble rústico", "", 232f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product("1", "1", "Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product("1", "1", "Mueble rústico", "", 232f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product("1", "1", "Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product("1", "1", "Mueble rústico", "", 232f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
    }*/

    fun getUnsoldProducts() {
        productRepository.getUnsoldProducts(object : ProductRepositoryCallback.ListProducts {
            override fun onResponse(products: List<Product>) {
                productListLiveData.value = Resource.success(products)
            }

            override fun onError(message: String) {
                productListLiveData.value = Resource.error(message, emptyList())
            }

            override fun onLoading() {
                productListLiveData.value = Resource.loading(emptyList())
            }
        })
    }

    fun getUnsoldProductsByCategory(category: Category) {
        productRepository.getUnsoldProductsByCategory(category.toString().toLowerCase(), object : ProductRepositoryCallback.ListProducts {
            override fun onResponse(products: List<Product>) {
                productListLiveData.value = Resource.success(products)
            }

            override fun onError(message: String) {
                productListLiveData.value = Resource.error(message, emptyList())
            }

            override fun onLoading() {
                productListLiveData.value = Resource.loading(emptyList())
            }

        })
    }
}