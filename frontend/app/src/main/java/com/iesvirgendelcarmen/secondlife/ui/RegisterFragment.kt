package com.iesvirgendelcarmen.secondlife.ui

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
import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.UserWithoutId
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryCallback
import com.iesvirgendelcarmen.secondlife.model.api.user.UserRepositoryRetrofit


class RegisterFragment : Fragment() {

    lateinit var nickname: EditText
    lateinit var name: EditText
    lateinit var surname1: EditText
    lateinit var surname2: EditText
    lateinit var phone: EditText
    lateinit var email: EditText
    lateinit var password1: EditText
    lateinit var password2: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nickname = view.findViewById(R.id.nickname)
        name = view.findViewById(R.id.name)
        surname1 = view.findViewById(R.id.surname1)
        surname2 = view.findViewById(R.id.surname2)
        phone = view.findViewById(R.id.phone)
        email = view.findViewById(R.id.email)
        password1 = view.findViewById(R.id.password1)
        password2 = view.findViewById(R.id.password2)

        val register = view.findViewById<Button>(R.id.register)
        val close = view.findViewById<ImageButton>(R.id.close)

        close.setOnClickListener(View.OnClickListener {
            exit()
        })

        register.setOnClickListener(View.OnClickListener {
            register()
        })
    }

    private fun register() {
        if (checkInput()) {
            Toast.makeText(context, "Te faltan por completar campos", Toast.LENGTH_SHORT).show()
        } else {
            if (password1.text.toString() == password2.text.toString()) {

                val user = UserWithoutId(
                    nickname.text.toString(),
                    name.text.toString(),
                    surname1.text.toString(),
                    surname2.text.toString(),
                    email.text.toString(),
                    password1.text.toString(),
                    phone.text.toString(),1,
                    ""
                )

                UserRepositoryRetrofit.register(user, object : UserRepositoryCallback.UserCallback {
                    override fun onResponse(user: User) {
                        Toast.makeText(context,"Registrado correctamente, inicia sesión", Toast.LENGTH_SHORT).show()
                        exit()
                    }

                    override fun onError(message: String?) {
                        Toast.makeText(context,"Error de conexion al registrarte", Toast.LENGTH_SHORT).show()
                    }

                    override fun onLoading() {}

                })
            } else {
                Toast.makeText(context, "La contraseña no es correcta", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkInput(): Boolean {
        return (nickname.text.toString() == "" ||
            name.text.toString() == "" ||
            surname1.text.toString() == "" ||
            email.text.toString() == "" ||
            password1.text.toString() == ""
        )
    }

    private fun exit() {
        activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
    }
}
