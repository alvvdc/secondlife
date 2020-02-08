package com.iesvirgendelcarmen.secondlife.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.Product

class DetailProductFragment(val product: Product) :Fragment() {

    private lateinit var publisherImage :ImageView
    private lateinit var productImage :ImageView
    private lateinit var publisherName :TextView
    private lateinit var productTitle :TextView
    private lateinit var productDescription :TextView
    private lateinit var productPrice :TextView
    private lateinit var productVisits :TextView
    private lateinit var contactButton :Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.product_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViewsById(view)

        productTitle.text = product.title
        productDescription.text = product.description
        productPrice.text = "${product.price}€"
    }

    private fun findViewsById(view: View) {
        publisherImage = view.findViewById(R.id.publisherImage)
        publisherName = view.findViewById(R.id.publisherName)
        productImage = view.findViewById(R.id.productImage)
        productTitle = view.findViewById(R.id.productTitle)
        productDescription = view.findViewById(R.id.productDescription)
        productPrice = view.findViewById(R.id.productPrice)
        productVisits = view.findViewById(R.id.productVisits)
        contactButton = view.findViewById(R.id.contactButton)
    }
}