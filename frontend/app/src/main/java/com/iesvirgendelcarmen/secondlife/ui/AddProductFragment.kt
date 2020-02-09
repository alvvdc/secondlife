package com.iesvirgendelcarmen.secondlife.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.Category
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.ProductViewModel
import com.iesvirgendelcarmen.secondlife.model.api.Resource

class AddProductFragment : Fragment() {

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProviders.of(this).get(ProductViewModel::class.java)
    }

    lateinit var titleEditText :EditText
    lateinit var descriptionEditText :EditText
    lateinit var priceEditText :EditText
    lateinit var categorySpinner :Spinner
    lateinit var save :Button
    lateinit var loadImage :Button

    private val NOT_SELECTED_CATEGORY = "Categoría"

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
                productViewModel.insertNewProduct(Product("", "5e3517e17e13c20483c3750d", formProduct.title, formProduct.description, formProduct.price.toFloat(), mutableListOf(), Category.parse(formProduct.category)))
            } else {
                Toast.makeText(context, "Debes rellenar todos los campos", Toast.LENGTH_LONG).show()
            }
        }
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
                    fragmentManager?.popBackStack()
                }
            }
        })
    }
}