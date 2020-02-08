package com.iesvirgendelcarmen.secondlife.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.ProductViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.Category
import com.iesvirgendelcarmen.secondlife.model.ProductRecyclerViewAdapter


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProviders.of(this).get(ProductViewModel::class.java)
    }

    lateinit var listProductsFragment: ListProductsFragment

    lateinit var drawerLayout : DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar()

        val productViewListener = onClickProductForDetail()
        val fapCallback = onClickedFapListener()

        listProductsFragment = ListProductsFragment(productViewModel, toolbar, fapCallback, productViewListener)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, listProductsFragment)
                .commit()
        }

        var sharedPreferences = getSharedPreferences(APIConfig.CONFIG_FILE,0)

        if (sharedPreferences.getString("token", "null") == "null"){
            supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, LoginFragment())
                .commit()
        }

    }

    private fun onClickProductForDetail(): ProductRecyclerViewAdapter.ProductViewListener {
        return object : ProductRecyclerViewAdapter.ProductViewListener {
            override fun onClick(product: Product) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, DetailProductFragment(product))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun onClickedFapListener(): FragmentManager.FAP {
        return object : FragmentManager.FAP {
            override fun onClick() {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, AddProductFragment(productViewModel))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun toolBar() {
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        setSupportActionBar(toolbar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        if (listProductsFragment == null)
            return false

        when (menuItem.itemId) {
            R.id.motor -> {
                listProductsFragment.listProductsByCategory(Category.MOTOR)
            }
            R.id.inmobiliaria -> {
                listProductsFragment.listProductsByCategory(Category.INMOBILIARIA)
            }
            R.id.empleo -> {
                listProductsFragment.listProductsByCategory(Category.EMPLEO)
            }
            R.id.servicios -> {
                listProductsFragment.listProductsByCategory(Category.SERVICIOS)
            }
            R.id.otros -> {
                listProductsFragment.listProductsByCategory(Category.OTROS)
            }
            R.id.inicio -> {
                listProductsFragment.listAllProducts()
            }
        }
        drawerLayout.closeDrawers()
        return true
    }

    interface FragmentManager {
        interface FAP {
            fun onClick()
        }
    }
}
