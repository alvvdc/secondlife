package com.iesvirgendelcarmen.secondlife.ui

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.Menu
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.iesvirgendelcarmen.secondlife.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.*
import com.iesvirgendelcarmen.secondlife.model.api.Resource
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

class MainActivity :    AppCompatActivity(),
                        NavigationView.OnNavigationItemSelectedListener,
                        DetailProductFragment.SubmitDetailProduct,
                        DetailProductFragment.ContactUserButton,
                        ListProductsFragment.AddProductFAP,
                        ProductRecyclerViewAdapter.ProductViewListener,
                        MainActions {

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProviders.of(this).get(ProductViewModel::class.java)
    }

    lateinit var listProductsFragment: ListProductsFragment
    lateinit var drawerLayout : DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(APIConfig.CONFIG_FILE,0)

        navigationDrawer()
        changeHeaderData()
        onSearchListener()

        listProductsFragment = ListProductsFragment()

        if (savedInstanceState == null) showProductsListFragment()
        if (!isThereTokenSaved()) showLoginFragment()
    }

    private fun showLoginFragment() {
        supportFragmentManager.beginTransaction().replace(android.R.id.content, LoginFragment()).addToBackStack(null).commit()
    }

    override fun onClickProductListElement(product: Product) {

        if (isThereTokenSaved())
        {
            val bundle = Bundle()
            bundle.putParcelable("PRODUCT", product)
            val detailProductFragment = DetailProductFragment()
            detailProductFragment.arguments = bundle

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, detailProductFragment)
                .addToBackStack(null)
                .commit()
        }
        else
        {
            showLoginFragment()
        }
    }

    override fun onClickedEditProductButton(product: Product) {

        val bundle = Bundle()
        bundle.putParcelable("PRODUCT", product)

        val editProductsFragment = EditProductFragment()
        editProductsFragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, editProductsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onClickContactUserButton(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
    }

    override fun onClickedFapForAddProduct() {
        if (isThereTokenSaved())
            supportFragmentManager.beginTransaction().replace(R.id.container, AddProductFragment()).addToBackStack(null).commit()
        else
            showLoginFragment()
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

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        changeToolbar(true, "")

        if (supportFragmentManager.findFragmentByTag("listProductFragment") == null)
            supportFragmentManager.beginTransaction().replace(R.id.container, listProductsFragment).commit()

        when (menuItem.itemId) {
            R.id.motor -> listProductsFragment.listProductsByCategory(Category.MOTOR)
            R.id.inmobiliaria -> listProductsFragment.listProductsByCategory(Category.INMOBILIARIA)
            R.id.empleo -> listProductsFragment.listProductsByCategory(Category.EMPLEO)
            R.id.servicios -> listProductsFragment.listProductsByCategory(Category.SERVICIOS)
            R.id.otros -> listProductsFragment.listProductsByCategory(Category.OTROS)
            R.id.inicio -> listProductsFragment.listAllProducts()
            R.id.login -> showLoginFragment()
            R.id.perfil -> showProfile()
            R.id.logout -> logout()
            R.id.misAnuncios -> {
                val userId = getSavedUserId()
                if (userId != "null") {
                    changeToolbar(false, "Mis anuncios")
                    listProductsFragment.listProductsByUserId(userId)
                }
                else showLoginFragment()
            }
        }
        drawerLayout.closeDrawers()

        val activeFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (activeFragment !is ListProductsFragment) supportFragmentManager.popBackStack()

        return true
    }

    private fun onSearchListener() {
        var busqueda: SearchView = toolbar.findViewById(R.id.busqueda)

        busqueda.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listProductsFragment.listProductsBySearch(newText.toString())
                return true
            }

        })
    }

    // Main actions:

    override fun showProfile() {

        val fragmentManager = supportFragmentManager
        val count = fragmentManager.backStackEntryCount
        for (i in 0 until count) {
            fragmentManager.popBackStackImmediate()
        }

        supportFragmentManager.beginTransaction().replace(R.id.container, ProfileFragment(sharedPreferences)).addToBackStack(null).commit()
        changeToolbar(false, "Perfil")
    }

    override fun showProductsListFragment() {
        changeToolbar(true, "")
        supportFragmentManager.beginTransaction().replace(R.id.container, listProductsFragment,"listProductFragment").commit()
    }

    override fun changeHeaderData() {
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
            editUser.isVisible = true

            userViewModel.getUser(userID, token)

            userViewModel.userLiveData.observe(this, Observer { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        nameLastName.text = "${resource.data.name} ${resource.data.lastName1} ${resource.data.lastName2}"
                        email.text = resource.data.email
                        val decoded = Base64.decode(resource.data.image, Base64.NO_WRAP)
                        val bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)
                        Glide.with(image).load(bitmap).into(image)
                    }
                    Resource.Status.ERROR ->
                        Toast.makeText(applicationContext, "Error al cargar tus datos", Toast.LENGTH_SHORT).show()
                }
            })

        } else {
            nameLastName.text = "Invitado"
            email.text = ""
            image.setImageResource(R.drawable.logo)
            loginButton.isVisible = true
            logoutButton.isVisible = false
            profileButton.isVisible = false
            editUser.isVisible = false
        }

        editUser.setOnClickListener {
            showProfile()
            drawerLayout.closeDrawers()
        }
    }

    override fun logout(){
        sharedPreferences.edit().remove("userID").remove("token").apply()
        changeHeaderData()
        showProductsListFragment()
    }

    override fun changeToolbar(search: Boolean, text: String){
        var title = toolbar.findViewById<TextView>(R.id.titleHead)
        var searchBox = toolbar.findViewById<SearchView>(R.id.busqueda)

        searchBox.isVisible = search
        title.isVisible = !search
        title.text = text
    }

    override fun getSavedUserToken() :String {
        if (sharedPreferences != null)
            return sharedPreferences.getString("token", "null").toString()
        return "null"
    }

    override fun getSavedUserId() :String {
        if (sharedPreferences != null)
            return sharedPreferences.getString("userID", "null").toString()
        return "null"
    }

    override fun isThereTokenSaved(): Boolean {
      return getSavedUserToken() != "null"
    }

}

interface MainActions{
    fun showProfile()
    fun changeHeaderData()
    fun logout()
    fun changeToolbar(search: Boolean, text: String)
    fun getSavedUserId() :String
    fun getSavedUserToken() :String
    fun showProductsListFragment()
    fun isThereTokenSaved(): Boolean
}
