package com.iesvirgendelcarmen.secondlife.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

import com.iesvirgendelcarmen.secondlife.R


class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var login = view!!.findViewById<Button>(R.id.login)
        var register = view!!.findViewById<Button>(R.id.register)
        var close = view!!.findViewById<ImageButton>(R.id.close)

        close.setOnClickListener(View.OnClickListener {
            activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        })

        login.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
        })

        register.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "Register", Toast.LENGTH_SHORT).show()
        })
    }
}

