package com.iesvirgendelcarmen.secondlife.ui

import android.app.ActionBar
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.Category
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.ProductViewModel
import com.iesvirgendelcarmen.secondlife.model.api.Resource
import java.io.ByteArrayOutputStream
import java.io.File

class AddProductFragment(val productViewModel: ProductViewModel) : Fragment() {

    lateinit var titleEditText :EditText
    lateinit var descriptionEditText :EditText
    lateinit var priceEditText :EditText
    lateinit var categorySpinner :Spinner
    lateinit var save :Button
    lateinit var loadImage :Button
    lateinit var buttonsLayout :LinearLayout

    private val NOT_SELECTED_CATEGORY = "Categoría"
    private val IMAGE_REQUEST_CODE = 1
    private val IMAGE_QUALITY = 10 // 0 .. 100 %

    private val loadedImages = mutableListOf<String>()

    data class FormProduct (val title :String, val description :String, val price :String, val category :String)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.add_product_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViewsById(view)
        loadSpinner(view)

        save.setOnClickListener {

            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val price = priceEditText.text.toString()
            val category = categorySpinner.selectedItem as String

            val formProduct = FormProduct(title, description, price, category)

            if (areFieldsFilled(formProduct)) {
                productViewModel.insertNewProduct(Product("", "5e3517e17e13c20483c3750d", formProduct.title, formProduct.description, formProduct.price.toFloat(), loadedImages, Category.parse(formProduct.category)))
            } else {
                Toast.makeText(context, "Debes rellenar todos los campos", Toast.LENGTH_LONG).show()
            }
        }

        loadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {

            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, data?.data)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val base64 = Base64.encodeToString(byteArray, Base64.NO_WRAP)

            val path = data?.data?.path
            val file = File(path)
            val filename = file.name
            loadedImages.add(base64)
            addButtonForImageAdded(filename)
        }
    }

    private fun addButtonForImageAdded(text :String) :Button {
        val button = Button(context, null, 0, R.style.AppTheme_ButtonCustom)
        button.text = text

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 10)
        button.layoutParams = layoutParams

        buttonsLayout.addView(button)
        return button
    }

    private fun areFieldsFilled(formProduct: FormProduct) :Boolean {
        if (formProduct.title.isEmpty() || formProduct.description.isEmpty() || formProduct.price.isEmpty() || formProduct.category == NOT_SELECTED_CATEGORY) {
            return false
        }
        return true
    }

    private fun findViewsById(view: View) {
        titleEditText = view.findViewById(R.id.title)
        descriptionEditText = view.findViewById(R.id.description)
        priceEditText = view.findViewById(R.id.price)
        save = view.findViewById(R.id.save)
        loadImage = view.findViewById(R.id.loadImages)
        buttonsLayout = view.findViewById(R.id.buttonsLayout)
    }

    private fun loadSpinner(view: View) {
        val categories = mutableListOf<String>()
        categories.add(NOT_SELECTED_CATEGORY)
        for (category in Category.values()) {
            categories.add(category.toString().toLowerCase().capitalize())
        }

        categorySpinner = view.findViewById(R.id.category)
        val adapter = ArrayAdapter(view.context, R.layout.spinner, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        productViewModel.productLiveData.observe(viewLifecycleOwner, Observer { resource ->

            when (resource.status) {
                Resource.Status.ERROR -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.SUCCESS -> {
                    Toast.makeText(context, "Enviado", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}