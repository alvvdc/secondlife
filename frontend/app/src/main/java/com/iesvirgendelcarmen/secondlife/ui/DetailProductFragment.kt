package com.iesvirgendelcarmen.secondlife.ui

import android.content.Context
import android.graphics.Bitmap
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

class DetailProductFragment(val product: Product, private val productViewModel :ProductViewModel, private val submitDetailProduct: SubmitDetailProduct) :Fragment() {

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

        (activity as MainActivity).changeToolbar(false, product.title)
        findViewsById(view)

        productTitle.text = product.title
        productDescription.text = product.description
        productPrice.text = "${product.price}€"


        val bitmapsList = mutableListOf<Bitmap>()
        for (image in product.images) {
            val decoded = Base64.decode(image, Base64.NO_WRAP)
            val bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)
            bitmapsList.add(bitmap)
        }

        var index = 0

        if (bitmapsList.size > 0) {
            setProductImage(bitmapsList[0])

            productImage.setOnClickListener {

                if (index+1 < bitmapsList.size) index += 1
                else index = 0

                setProductImage(bitmapsList[index])
            }
        }

        val mainActivity = activity as MainActivity
        val userId = mainActivity.getSavedUserId()
        if (userId == product.publisher)
        {
            contactButton.text = "Editar"
            contactButton.setOnClickListener { submitDetailProduct.onClick(product) }
        }
        else
        {
            // TODO: Fragmento para ver el perfil del autor del anuncio
        }
    }

    private fun setProductImage(image :Bitmap) {
        Glide
            .with(this)
            .load(image)
            .into(productImage)
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

    override fun onDetach() {
        (activity as MainActivity).changeToolbar(true, "")
        super.onDetach()
    }

    interface SubmitDetailProduct {
        fun onClick(product :Product)
    }
}