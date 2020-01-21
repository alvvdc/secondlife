package com.iesvirgendelcarmen.secondlife.model

import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {

    val productsList = mutableListOf<Product>()

    init {
        mock()
    }

    private fun mock() {
        productsList.add(Product(1, 1,"Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product(1, 1, "Mueble rústico", "", 130f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product(1, 1, "Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product(1, 1, "Mueble rústico", "", 130f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product(1, 1, "Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product(1, 1, "Mueble rústico", "", 130f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product(1, 1, "Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product(1, 1, "Mueble rústico", "", 130f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product(1, 1, "Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product(1, 1, "Mueble rústico", "", 130f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
    }
}