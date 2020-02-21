package com.iesvirgendelcarmen.secondlife.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.Category
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.ProductRecyclerViewAdapter
import com.iesvirgendelcarmen.secondlife.model.ProductViewModel
import com.iesvirgendelcarmen.secondlife.model.api.Resource

class ListProductsFragment :Fragment() {

    private lateinit var productViewListener: ProductRecyclerViewAdapter.ProductViewListener
    private lateinit var fapCallback :AddProductFAP

    private val productViewModel :ProductViewModel by lazy {
        ViewModelProviders.of(this).get(ProductViewModel::class.java)
    }

    var lastProductsListObtained = emptyList<Product>()
    lateinit var addProductFAP :FloatingActionButton
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var productRecyclerViewAdapter :ProductRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            productViewModel.getUnsoldProducts()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fapCallback = context as AddProductFAP
        productViewListener = context as ProductRecyclerViewAdapter.ProductViewListener
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

        onClickedFAP(view)

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener {
            productViewModel.getUnsoldProducts()
        }
    }

    private fun onClickedFAP(view: View) {
        addProductFAP = view.findViewById(R.id.addProductFAP)
        addProductFAP.setOnClickListener {
            fapCallback.onClickedFapForAddProduct()
        }
    }

    override fun onStart() {
        super.onStart()

        productViewModel.productListLiveData.observe(viewLifecycleOwner, Observer {resource ->

            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    productRecyclerViewAdapter.productsList = resource.data
                    productRecyclerViewAdapter.notifyDataSetChanged()
                    lastProductsListObtained = resource.data
                    swipeRefreshLayout.isRefreshing = false
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, "Comprueba tu conexión", Toast.LENGTH_LONG).show()
                    swipeRefreshLayout.isRefreshing = false
                }
                Resource.Status.LOADING -> {
                    swipeRefreshLayout.isRefreshing = true
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

    fun listProductsByUserId(userId :String) {
        val filteredProductList = lastProductsListObtained.filter { product -> product.publisher == userId }
        productRecyclerViewAdapter.productsList = filteredProductList
        productRecyclerViewAdapter.notifyDataSetChanged()
    }

    fun listAllProducts() {
        //productViewModel.getUnsoldProducts()

        productRecyclerViewAdapter.productsList = lastProductsListObtained
        productRecyclerViewAdapter.notifyDataSetChanged()
    }

    fun listProductsBySearch(query :String) {
        if (::productRecyclerViewAdapter.isInitialized) {
            productRecyclerViewAdapter.filter.filter(query)
        }
    }

    interface AddProductFAP {
        fun onClickedFapForAddProduct()
    }
}