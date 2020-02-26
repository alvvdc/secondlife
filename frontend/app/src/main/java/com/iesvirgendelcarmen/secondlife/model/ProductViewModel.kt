package com.iesvirgendelcarmen.secondlife.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iesvirgendelcarmen.secondlife.model.api.*
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryCallback
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryDataSource
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryRetrofit
import com.iesvirgendelcarmen.secondlife.model.api.product.ProductRepositoryVolley
import com.iesvirgendelcarmen.secondlife.ui.MainActivity

class ProductViewModel : ViewModel() {

    val productListLiveData = MutableLiveData<Resource<List<Product>>>()
    val productLiveData = MutableLiveData<Resource<Product>>()
    val productVisitsLiveData = MutableLiveData<Resource<ProductVisits>>()
    val productListByUserLiveData = MutableLiveData<Resource<List<Product>>>()

    private val productRepository : ProductRepositoryDataSource = ProductRepositoryVolley


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

    fun insertNewProduct(product: Product, token :String) {
        productRepository.postNewProduct(product, token, object : ProductRepositoryCallback.OneProduct {
            override fun onResponse(product: Product) {
                productLiveData.value = Resource.success(product)
            }

            override fun onError(message: String) {
                productLiveData.value = Resource.error(message, Product("", "", "", "", 0f, mutableListOf(), Category.OTROS))
            }

        })
    }

    fun updateProduct(product: Product, token :String) {
        productRepository.updateProduct(product, token, object : ProductRepositoryCallback.OneProduct {
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

    fun getProductsByUser(userId :String) {

        productRepository.getProductsByUser(userId, object : ProductRepositoryCallback.ListProducts {
            override fun onResponse(products: List<Product>) {
                productListByUserLiveData.value = Resource.success(products)
            }

            override fun onError(message: String) {
                productListByUserLiveData.value = Resource.error(message, emptyList())
            }

            override fun onLoading() {
                productListByUserLiveData.value = Resource.loading(emptyList())
            }
        })
    }
}