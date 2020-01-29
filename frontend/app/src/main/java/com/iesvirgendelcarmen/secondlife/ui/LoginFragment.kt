package com.iesvirgendelcarmen.secondlife.ui

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
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

        var login = view!!.findViewById<Button>(R.id.login)
        var register = view!!.findViewById<Button>(R.id.register)
        var close = view!!.findViewById<ImageButton>(R.id.close)

        var email = view!!.findViewById<EditText>(R.id.email)
        var password = view!!.findViewById<EditText>(R.id.password)

        close.setOnClickListener(View.OnClickListener {
            exit()
        })

        login.setOnClickListener(View.OnClickListener {

            val user = User(0, "", "", "", "", email.text.toString(), password.text.toString(), "", 0)
            UserRepositoryRetrofit.login(user, object: UserRepositoryCallback.TokenCallback{
                override fun onResponse(token: String) {
                    var sharedPreferences = context!!.getSharedPreferences(APIConfig.CONFIG_FILE,0)
                    sharedPreferences.edit().putString("token", token).apply()
                    exit()
                }

                override fun onError(message: String?) {
                    Toast.makeText(context, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }

                override fun onLoading() {
                }
            })

        })

        register.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "Register", Toast.LENGTH_SHORT).show()
        })
    }

    private fun exit() {
        activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
    }
}

