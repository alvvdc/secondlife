package com.iesvirgendelcarmen.secondlife.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.Category
import com.iesvirgendelcarmen.secondlife.model.ProductRecyclerViewAdapter
import com.iesvirgendelcarmen.secondlife.model.ProductViewModel
import com.iesvirgendelcarmen.secondlife.model.api.Resource

class ListProductsFragment(private val productViewModel: ProductViewModel, var toolbar: View) :Fragment() {

    lateinit var productRecyclerViewAdapter :ProductRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_list_products, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerViewProducts = view.findViewById<RecyclerView>(R.id.recyclerViewProducts)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter(emptyList())

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

    override fun onStart() {
        super.onStart()

        productViewModel.productListLiveData.observe(viewLifecycleOwner, Observer {resource ->

            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    productRecyclerViewAdapter.productsList = resource.data
                    productRecyclerViewAdapter.notifyDataSetChanged()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {

                }
            }
        })

        productViewModel.getUnsoldProducts()
    }

    fun listProductsByCategory(category: Category) {
        productViewModel.getUnsoldProductsByCategory(category)
    }
}