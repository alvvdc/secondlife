package com.iesvirgendelcarmen.secondlife.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.ProductRecyclerViewAdapter
import com.iesvirgendelcarmen.secondlife.model.ProductViewModel

class ListProductsFragment(private val productViewModel: ProductViewModel, var toolbar: View) :Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_list_products, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerViewProducts = view.findViewById<RecyclerView>(R.id.recyclerViewProducts)
        val productRecyclerViewAdapter = ProductRecyclerViewAdapter(productViewModel.productsList)

        recyclerViewProducts.apply {
            adapter = productRecyclerViewAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        var busqueda : SearchView = toolbar.findViewById(R.id.busqueda)

        busqueda.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productRecyclerViewAdapter.filter.filter(newText.toString())
                return true
            }

        })


    }
}