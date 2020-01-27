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
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.api.ProductRepositoryCallback
import com.iesvirgendelcarmen.secondlife.model.api.ProductRepositoryRetrofit
import com.iesvirgendelcarmen.secondlife.model.api.ProductRepositoryVolley
import com.iesvirgendelcarmen.secondlife.model.Category


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

        listProductsFragment = ListProductsFragment(productViewModel, toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, listProductsFragment)
                .commit()
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
        }
        drawerLayout.closeDrawers()
        return true
    }
}
