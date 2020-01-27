package com.iesvirgendelcarmen.secondlife.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProviders.of(this).get(ProductViewModel::class.java)
    }

    lateinit var drawerLayout : DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, ListProductsFragment(productViewModel, toolbar))
                .commit()
        }

        val lista = mutableListOf("https://i.imgur.com/0oPAfru.png", "https://i.imgur.com/0oPAfru.png")
        ProductRepositoryVolley.editProduct(Product(1, 1, "Pocas camisetas", "Están nuevas a estrenar, no hago envios.", 20f, lista),
            object: ProductRepositoryCallback.EditProduct{
                override fun onResponse(product: Product) {
                    Log.i("pepe", product.title)
                }

                override fun onError(message: String) {
                    Log.i("pepe", "message")
                }

                override fun onLoading() {
                    Log.i("pepe", "Cargando")
                }
            } )
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

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {


        return true
    }
}
