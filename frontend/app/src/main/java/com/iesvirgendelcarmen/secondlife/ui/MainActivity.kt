package com.iesvirgendelcarmen.secondlife.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.ProductViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView
import android.widget.Toast
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Category
import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryCallback
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryRetrofit


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProviders.of(this).get(ProductViewModel::class.java)
    }

    lateinit var listProductsFragment: ListProductsFragment
    lateinit var drawerLayout : DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var sharedPreferences: SharedPreferences
    lateinit var userID: String
    lateinit var token: String
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this
        sharedPreferences = getSharedPreferences(APIConfig.CONFIG_FILE,0)
        token = sharedPreferences.getString("token", "null")!!
        userID = sharedPreferences.getString("userID", "null")!!
        navigationDrawer()
        changeHeaderData()

        listProductsFragment = ListProductsFragment(productViewModel, toolbar)

        if (savedInstanceState == null) chargeProducts()

        if (token == "null")
            supportFragmentManager.beginTransaction().add(android.R.id.content, LoginFragment()).commit()
    }

    fun chargeProducts() {
            supportFragmentManager.beginTransaction().add(
                R.id.container,
                listProductsFragment,
                "listProductFragment"
            ).commit()
    }

    private fun navigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        setSupportActionBar(toolbar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view.setNavigationItemSelectedListener(this)
    }

    fun changeHeaderData() {
        var navigationView = findViewById<NavigationView>(R.id.nav_view).getHeaderView(0)
        var editUser = navigationView.findViewById<ImageButton>(R.id.editUser)
        var nameLastName = navigationView.findViewById<TextView>(R.id.nameLastName)
        var email = navigationView.findViewById<TextView>(R.id.email)

        token = sharedPreferences.getString("token", "null")!!
        userID = sharedPreferences.getString("userID", "null")!!

        if (token != "null") {
            UserRepositoryRetrofit.getUser(userID, token,
                object : UserRepositoryCallback.UserCallback {
                    override fun onError(message: String?) {
                        Toast.makeText(context, "Error al cargar tus datos", Toast.LENGTH_SHORT).show()
                    }

                    override fun onLoading() {
                    }

                    override fun onResponse(user: User) {
                        nameLastName.text = "${user.name} ${user.lastName1} ${user.lastName2}"
                        email.text = user.email
                    }
                })
        } else {
            nameLastName.text = "Invitado"
            email.text = ""
        }

        editUser.setOnClickListener(View.OnClickListener {
            if (token != "null") {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ProfileFragment(sharedPreferences)).commit()
                drawerLayout.closeDrawers()
            } else {
                Toast.makeText(context, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        if (supportFragmentManager.findFragmentByTag("listProductFragment") == null)
            supportFragmentManager.beginTransaction().replace(R.id.container, listProductsFragment).commit()

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
            R.id.inicio -> {
                listProductsFragment.listAllProducts()
            }
        }
        drawerLayout.closeDrawers()
        return true
    }

}
