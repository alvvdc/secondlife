package com.iesvirgendelcarmen.secondlife.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.*

class DetailProductFragment :Fragment() {

    private lateinit var product :Product
    private lateinit var submitDetailProduct :SubmitDetailProduct
    private lateinit var contactUserButton :ContactUserButton

    private val productViewModel :ProductViewModel by lazy {
        ViewModelProviders.of(this).get(ProductViewModel::class.java)
    }

    lateinit var userId :String

    private val userViewModel :UserViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    private lateinit var publisherImage :ImageView
    private lateinit var productImage :ImageView
    private lateinit var publisherName :TextView
    private lateinit var productTitle :TextView
    private lateinit var productDescription :TextView
    private lateinit var productPrice :TextView
    private lateinit var productVisits :TextView
    private lateinit var contactButton :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            product = arguments?.getParcelable("PRODUCT") ?: Product("", "", "", "", 0f, mutableListOf(), Category.OTROS, false)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        submitDetailProduct = context as SubmitDetailProduct
        contactUserButton = context as ContactUserButton

        val mainActivity = activity as MainActivity
        userId = mainActivity.getSavedUserId()
    }

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

        if (userId == product.publisher)
        {
            contactButton.text = "Editar"
            contactButton.setOnClickListener { submitDetailProduct.onClickedEditProductButton(product) }
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

    private fun setUserContactInfo(user :UserContact) {

        publisherName.text = "${user.name} ${user.lastName1}"

        if (user.image.isNotEmpty()) {
            val decoded = Base64.decode(user.image, Base64.NO_WRAP)
            val bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)

            Glide
                .with(this)
                .load(bitmap)
                .into(publisherImage)
        }

        if (userId != product.publisher) {
            contactButton.setOnClickListener {
                contactUserButton.onClickContactUserButton(user.phone)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        productViewModel.productVisitsLiveData.observe(viewLifecycleOwner, Observer {
            productVisits.text = it.data.visits.toString()
        })
        
        productViewModel.visitProduct(product._id)

        userViewModel.userContactLiveData.observe(viewLifecycleOwner, Observer {
            setUserContactInfo(it.data)
        })

        userViewModel.getUserContactInfo(product.publisher, getUserToken())
    }

    override fun onDetach() {
        (activity as MainActivity).changeToolbar(true, "")
        super.onDetach()
    }

    private fun getUserToken() :String {
        return (activity as MainActivity).getSavedUserToken()
    }

    interface SubmitDetailProduct {
        fun onClickedEditProductButton(product :Product)
    }

    interface ContactUserButton {
        fun onClickContactUserButton(phone :String)
    }
}