package com.iesvirgendelcarmen.secondlife.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.iesvirgendelcarmen.secondlife.R
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.*
import com.iesvirgendelcarmen.secondlife.model.api.Resource
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryCallback
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryRetrofit
import androidx.lifecycle.ViewModelProviders


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

        val productViewListener = onClickProductForDetail()
        val fapCallback = onClickedFapListener()

        listProductsFragment = ListProductsFragment(productViewModel, toolbar, fapCallback, productViewListener)

        if (savedInstanceState == null) chargeProducts()

        if (token == "null")
            showLogin()
    }

    private fun showLogin() {
        supportFragmentManager.beginTransaction().add(android.R.id.content, LoginFragment())
            .commit()
    }

    fun chargeProducts() {
            supportFragmentManager.beginTransaction().replace(
                R.id.container,
                listProductsFragment,
                "listProductFragment"
            ).commit()
    }

    private fun onClickProductForDetail(): ProductRecyclerViewAdapter.ProductViewListener {
        return object : ProductRecyclerViewAdapter.ProductViewListener {
            override fun onClick(product: Product) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, DetailProductFragment(product, productViewModel))
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
                    .replace(R.id.container, AddProductFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
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

    fun logout(){
        sharedPreferences.edit()
            .remove("userID")
            .remove("token")
            .apply()
        changeHeaderData()
        chargeProducts()
    }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    fun changeHeaderData() {
        var navigationView = findViewById<NavigationView>(R.id.nav_view).getHeaderView(0)
        var editUser = navigationView.findViewById<ImageButton>(R.id.editUser)
        var nameLastName = navigationView.findViewById<TextView>(R.id.nameLastName)
        var email = navigationView.findViewById<TextView>(R.id.email)
        var menu: Menu = findViewById<NavigationView>(R.id.nav_view).menu
        var profileButton = menu.findItem(R.id.perfil)
        var loginButton = menu.findItem(R.id.login)
        var logoutButton = menu.findItem(R.id.logout)

        token = sharedPreferences.getString("token", "null")!!
        userID = sharedPreferences.getString("userID", "null")!!

        if (token != "null") {

            loginButton.isVisible = false
            logoutButton.isVisible = true
            profileButton.isVisible = true

   /*         userViewModel.getUser(userID, token)

            userViewModel.userLiveData.observe(viewLifecycleOwner, Observer { resource ->

                when (resource.status) {
                    Resource.Status.SUCCESS -> {

                    }
                    Resource.Status.ERROR -> {

                    }
                }
            })*/

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

            loginButton.isVisible = true
            logoutButton.isVisible = false
            profileButton.isVisible = false
        }

        editUser.setOnClickListener(View.OnClickListener {
            if (token != "null") {
                showProfile()
                drawerLayout.closeDrawers()
            } else {
                Toast.makeText(context, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showProfile() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ProfileFragment(sharedPreferences)).commit()
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
            R.id.otros -> {
                listProductsFragment.listProductsByCategory(Category.OTROS)
            }
            R.id.inicio -> {
                listProductsFragment.listAllProducts()
            }
            R.id.login -> {
                showLogin()
            }
            R.id.perfil -> {
                showProfile()
            }
            R.id.logout -> {
                logout()
            }
        }
        drawerLayout.closeDrawers()


        val activeFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (activeFragment !is ListProductsFragment) {
            supportFragmentManager.popBackStack()
        }
        return true
    }

    interface FragmentManager {
        interface FAP {
            fun onClick()
        }
    }
}
