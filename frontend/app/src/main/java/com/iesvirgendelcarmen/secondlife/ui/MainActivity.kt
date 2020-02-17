package com.iesvirgendelcarmen.secondlife.ui

import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
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
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProviders.of(this).get(ProductViewModel::class.java)
    }

    lateinit var listProductsFragment: ListProductsFragment
    lateinit var drawerLayout : DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(APIConfig.CONFIG_FILE,0)

        navigationDrawer()
        changeHeaderData()

        val productViewListener = onClickProductForDetail()
        val fapCallback = onClickedFapListener()

        listProductsFragment = ListProductsFragment(productViewModel, toolbar, fapCallback, productViewListener)

        if (savedInstanceState == null) showProductsListFragment()

        if (!isThereTokenSaved())
            showLoginFragment()
    }

    private fun showLoginFragment() {
        supportFragmentManager.beginTransaction().add(android.R.id.content, LoginFragment())
            .commit()
    }

    fun getSavedUserToken() :String {
        if (sharedPreferences != null)
            return sharedPreferences.getString("token", "null").toString()
        return "null"
    }

    fun getSavedUserId() :String {
        if (sharedPreferences != null)
            return sharedPreferences.getString("userID", "null").toString()
        return "null"
    }

    fun isThereTokenSaved() = getSavedUserToken() != "null"

    fun showProductsListFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.container,
            listProductsFragment,
            "listProductFragment"
        ).commit()

    }

    private fun onSubmitDetailProduct(): DetailProductFragment.SubmitDetailProduct {
        return object : DetailProductFragment.SubmitDetailProduct {
            override fun onClick(product :Product) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, EditProductFragment(product))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun onClickProductForDetail(): ProductRecyclerViewAdapter.ProductViewListener {
        return object : ProductRecyclerViewAdapter.ProductViewListener {
            override fun onClick(product: Product) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, DetailProductFragment(product, productViewModel, onSubmitDetailProduct()))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun onClickedFapListener(): FragmentManager.FAP {
        return object : FragmentManager.FAP {
            override fun onClick() {

                if (isThereTokenSaved())
                {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, AddProductFragment())
                        .addToBackStack(null)
                        .commit()
                }
                else
                {
                    showLoginFragment()
                }
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
        showProductsListFragment()
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

        var image = navigationView.findViewById<ImageView>(R.id.image)

        val token = getSavedUserToken()
        val userID = getSavedUserId()

        if (token != "null") {

            loginButton.isVisible = false
            logoutButton.isVisible = true
            profileButton.isVisible = true

            UserRepositoryRetrofit.getUser(userID, token,
                object : UserRepositoryCallback.UserCallback {
                    override fun onError(message: String?) {
                        Toast.makeText(applicationContext, "Error al cargar tus datos", Toast.LENGTH_SHORT).show()
                    }

                    override fun onLoading() {
                    }

                    override fun onResponse(user: User) {
                        nameLastName.text = "${user.name} ${user.lastName1} ${user.lastName2}"
                        email.text = user.email

                        val decoded = Base64.decode(user.image, Base64.NO_WRAP)
                        val bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)

                        Glide
                            .with(image)
                            .load(bitmap)
                            .into(image)


                    }
                })
        } else {
            nameLastName.text = "Invitado"
            email.text = ""
            image.setImageResource(R.drawable.logo)
            loginButton.isVisible = true
            logoutButton.isVisible = false
            profileButton.isVisible = false
        }

        editUser.setOnClickListener(View.OnClickListener {
            if (token != "null") {
                showProfile()
                drawerLayout.closeDrawers()
            } else {
                Toast.makeText(applicationContext, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun showProfile() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ProfileFragment(sharedPreferences)).commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

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
                showLoginFragment()
            }
            R.id.perfil -> {
                showProfile()
            }
            R.id.logout -> {
                logout()
            }
            R.id.misAnuncios -> {
                val userId = getSavedUserId()

                if (userId != "null") listProductsFragment.listProductsByUserId(userId)
                else showLoginFragment()
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
