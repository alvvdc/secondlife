package com.iesvirgendelcarmen.secondlife.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.ProductViewModel
import com.iesvirgendelcarmen.secondlife.model.api.Resource

class DetailProductFragment(val product: Product, val productViewModel :ProductViewModel) :Fragment() {

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

        if (product.images.size > 0) {

            val decoded = Base64.decode(product.images[0], Base64.NO_WRAP)
            val bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)

            Glide
                .with(productImage)
                .load(bitmap)
                .into(productImage)
        }
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

    override fun onStart() {
        super.onStart()

        productViewModel.productVisitsLiveData.observe(viewLifecycleOwner, Observer {
            productVisits.text = it.data.visits.toString()
        })
        
        productViewModel.visitProduct(product._id)
    }
}