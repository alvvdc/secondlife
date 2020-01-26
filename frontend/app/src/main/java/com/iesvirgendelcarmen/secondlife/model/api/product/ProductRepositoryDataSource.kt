package com.iesvirgendelcarmen.secondlife.model.api.product

import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryCallback

interface ProductRepositoryDataSource {
    fun getAllProducts(callback : UserRepositoryCallback.ListProducts)
}