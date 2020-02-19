package com.iesvirgendelcarmen.secondlife.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.UserViewModel
import com.iesvirgendelcarmen.secondlife.model.api.Resource

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login, container, false)
    }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var login = view.findViewById<Button>(R.id.login)
        var register = view.findViewById<Button>(R.id.register)
        var close = view.findViewById<ImageButton>(R.id.close)
        var email = view.findViewById<EditText>(R.id.email)
        var password = view.findViewById<EditText>(R.id.password)

        close.setOnClickListener { exit() }
        login.setOnClickListener { loginUser(email, password) }

        register.setOnClickListener {
            fragmentManager!!.beginTransaction().add(android.R.id.content, RegisterFragment()).commit()
        }
    }

    private fun loginUser(email: EditText, password: EditText) {

        val user = User("", "", "", "", "", email.text.toString(), password.text.toString(), "", 9, "")
        userViewModel.login(user)

        userViewModel.tokenLiveData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    var sharedPreferences = context!!.getSharedPreferences(APIConfig.CONFIG_FILE, 0)
                    sharedPreferences.edit().putString("token", resource.data.token).putString("userID", resource.data.userId).apply()
                    (activity as MainActivity).changeHeaderData()
                    exit()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun exit() {
        activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
    }
}

