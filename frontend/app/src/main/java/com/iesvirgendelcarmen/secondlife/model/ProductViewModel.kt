package com.iesvirgendelcarmen.secondlife.model

import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {

    val productsList = mutableListOf<Product>()

    init {
        mock()
    }

    private fun mock() {
        productsList.add(Product("Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product("Mueble rústico", "", 130f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product("Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product("Mueble rústico", "", 130f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product("Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product("Mueble rústico", "", 130f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product("Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product("Mueble rústico", "", 130f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
        productsList.add(Product("Muchas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, mutableListOf("https://i.imgur.com/0oPAfru.png")))
        productsList.add(Product("Mueble rústico", "", 130f, mutableListOf("https://i.imgur.com/rWkb5AJ.png")))
    }
}