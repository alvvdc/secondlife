package com.iesvirgendelcarmen.secondlife.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.Category
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.ProductRecyclerViewAdapter
import com.iesvirgendelcarmen.secondlife.model.ProductViewModel
import com.iesvirgendelcarmen.secondlife.model.api.Resource

class ListProductsFragment(private val productViewModel: ProductViewModel, var toolbar: View, val fapCallback :MainActivity.FragmentManager.FAP, val productViewListener: ProductRecyclerViewAdapter.ProductViewListener) :Fragment() {

    var lastProductsListObtained = emptyList<Product>()
    lateinit var addProductFAP :FloatingActionButton
    lateinit var productRecyclerViewAdapter :ProductRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            productViewModel.getUnsoldProducts()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_list_products, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerViewProducts = view.findViewById<RecyclerView>(R.id.recyclerViewProducts)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter(emptyList(), productViewListener)

        recyclerViewProducts.apply {
            adapter = productRecyclerViewAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        onSearchListener()
        onClickedFAP(view)
    }

    private fun onClickedFAP(view: View) {
        addProductFAP = view.findViewById(R.id.addProductFAP)
        addProductFAP.setOnClickListener {
            fapCallback.onClick()
        }
    }

    private fun onSearchListener() {
        var busqueda: SearchView = toolbar.findViewById(R.id.busqueda)

        busqueda.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
                    lastProductsListObtained = resource.data
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {

                }
            }
        })
    }

    fun listProductsByCategory(category: Category) {
        //productViewModel.getUnsoldProductsByCategory(category)

        val filteredProductList = lastProductsListObtained.filter { product -> product.category == category }
        productRecyclerViewAdapter.productsList = filteredProductList
        productRecyclerViewAdapter.notifyDataSetChanged()
    }

    fun listAllProducts() {
        //productViewModel.getUnsoldProducts()

        productRecyclerViewAdapter.productsList = lastProductsListObtained
        productRecyclerViewAdapter.notifyDataSetChanged()
    }
}