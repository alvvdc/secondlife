package com.iesvirgendelcarmen.secondlife.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.Category

class AddProductFragment : Fragment() {

    lateinit var categorySpinner :Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.add_product_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val categories = mutableListOf<String>()
        categories.add("Categoría")
        for (category in Category.values()) {
            categories.add(category.toString().toLowerCase().capitalize())
        }

        categorySpinner = view.findViewById(R.id.category)
        val adapter = ArrayAdapter(view.context, R.layout.spinner, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
    }
}