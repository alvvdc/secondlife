package com.iesvirgendelcarmen.secondlife.ui

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Token
import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryCallback
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryDataSource
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryRetrofit


class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var login = view.findViewById<Button>(R.id.login)
        var register = view.findViewById<Button>(R.id.register)
        var close = view.findViewById<ImageButton>(R.id.close)

        var email = view.findViewById<EditText>(R.id.email)
        var password = view.findViewById<EditText>(R.id.password)

        close.setOnClickListener(View.OnClickListener {
            exit()
        })

        login.setOnClickListener(View.OnClickListener {
            loginUser(email, password)
        })

        register.setOnClickListener(View.OnClickListener {
            fragmentManager!!
                .beginTransaction()
                .add(android.R.id.content, RegisterFragment())
                .commit()
        })
    }

    private fun loginUser(email: EditText, password: EditText) {
        val user =
            User("", "", "", "", "", email.text.toString(), password.text.toString(), "", 9, "")
        UserRepositoryRetrofit.login(user, object : UserRepositoryCallback.TokenCallback {
            override fun onResponse(token: Token) {

                var sharedPreferences = context!!.getSharedPreferences(APIConfig.CONFIG_FILE, 0)
                sharedPreferences.edit()
                    .putString("token", token.token)
                    .putString("userID", token.userId)
                    .apply()
                exit()
            }

            override fun onError(message: String?) {
                Toast.makeText(context, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onLoading() {
            }
        })
    }

    private fun exit() {
        activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
    }
}

