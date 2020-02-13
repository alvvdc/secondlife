package com.iesvirgendelcarmen.secondlife.model

object ProductMapper {
    fun transformObjectBoToDto(product: Product) = ProductDto(product.publisher, product.title, product.description, product.price, product.images, product.category, product.isSold)
}