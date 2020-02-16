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
    val productLiveData = MutableLiveData<Resource<Product>>()
    val productVisitsLiveData = MutableLiveData<Resource<ProductVisits>>()

    private val productRepository : ProductRepositoryDataSource = ProductRepositoryVolley

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
        productRepository.getUnsoldProductsByCategory(category.toString(), object : ProductRepositoryCallback.ListProducts {
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

    fun insertNewProduct(product: Product) {
        productRepository.postNewProduct(product, object : ProductRepositoryCallback.OneProduct {
            override fun onResponse(product: Product) {
                productLiveData.value = Resource.success(product)
            }

            override fun onError(message: String) {
                productLiveData.value = Resource.error(message, Product("", "", "", "", 0f, mutableListOf(), Category.OTROS))
            }

        })
    }

    fun updateProduct(product: Product) {
        productRepository.updateProduct(product, object : ProductRepositoryCallback.OneProduct {
            override fun onResponse(product: Product) {
                productLiveData.value = Resource.success(product)
            }

            override fun onError(message: String) {
                productLiveData.value = Resource.error(message, Product("", "", "", "", 0f, mutableListOf(), Category.OTROS))
            }

        })
    }

    fun visitProduct(productId :String) {
        productRepository.visitProduct(productId, object : ProductRepositoryCallback.VisitProduct {
            override fun onResponse(productVisits: ProductVisits) {
                productVisitsLiveData.value = Resource.success(productVisits)
            }

            override fun onError(message: String) {
                productVisitsLiveData.value = Resource.error(message, ProductVisits("", -1))
            }

        })
    }
}