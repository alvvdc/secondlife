package com.iesvirgendelcarmen.secondlife.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.iesvirgendelcarmen.secondlife.model.User
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.UserViewModel
import com.iesvirgendelcarmen.secondlife.model.api.Resource

class ProfileFragment(val sharedPreferences: SharedPreferences): Fragment() {

    lateinit var nickname: EditText
    lateinit var name: EditText
    lateinit var lastName1: EditText
    lateinit var lastName2: EditText
    lateinit var phone: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var confirmPassword: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile, container, false)
    }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nickname = view.findViewById(R.id.nickname)
        name = view.findViewById(R.id.name)
        lastName1 = view.findViewById(R.id.lastName1)
        lastName2 = view.findViewById(R.id.lastName2)
        phone = view.findViewById(R.id.phone)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)
        confirmPassword = view.findViewById(R.id.confirmPassword)

        val saveButton = view.findViewById<Button>(R.id.save)
        val deleteAccountButton = view.findViewById<Button>(R.id.deleteAccount)

        val userID = sharedPreferences.getString("userID", "null")!!
        val token = sharedPreferences.getString("token", "null")!!

        var type = 0
        var image = ""

        userViewModel.getUser(userID, token)

        userViewModel.userLiveData.observe(viewLifecycleOwner, Observer { resource ->

            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    nickname.setText(resource.data.nickname)
                    name.setText(resource.data.name)
                    lastName1.setText(resource.data.lastName1)
                    lastName2.setText(resource.data.lastName2)
                    phone.setText(resource.data.phone)
                    email.setText(resource.data.email)
                    type = resource.data.type
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, "Error al cargar tus datos", Toast.LENGTH_SHORT).show()
                }
            }
        })

        saveButton.setOnClickListener { saveUser(userID, type, image, token) }

        deleteAccountButton.setOnClickListener {
            AlertDialog.Builder(context!!)
                .setTitle("Borrar cuenta")
                .setMessage("Se va a borrar tu cuenta y tus datos permanentemente, no la podrás volver a recuperar, ¿Deseas continuar?")
                .setPositiveButton(android.R.string.yes
                ) { dialog, which ->

                    userViewModel.deleteUser(userID, token)

                    userViewModel.deleteLiveData.observe(viewLifecycleOwner, Observer { resource ->

                        when (resource.status) {
                            Resource.Status.SUCCESS -> {
                                (activity as MainActivity).logout()
                            }
                            Resource.Status.ERROR -> {
                                Toast.makeText(context, "Error al borrar tu usuario", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.stat_sys_warning)
                .show()
        }
    }

    private fun saveUser(userID: String, type: Int, image: String, token: String) {

        if (checkPassword()) {

            if (checkInput()) {

                val user = User(
                    userID,
                    nickname.text.toString(),
                    name.text.toString(),
                    lastName1.text.toString(),
                    lastName2.text.toString(),
                    email.text.toString(),
                    password.text.toString(),
                    phone.text.toString(),
                    type,
                    image
                )

                userViewModel.editUser(user, token)

                userViewModel.userLiveData.observe(viewLifecycleOwner, Observer { resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            Toast.makeText(context,"Usuario actualizado con exito", Toast.LENGTH_SHORT).show()
                            (activity as MainActivity).changeHeaderData()
                        }
                        Resource.Status.ERROR -> {
                            Toast.makeText(context,"Error al actualizar tu usuario", Toast.LENGTH_SHORT).show()
                        }
                    }
                })

            } else {
                Toast.makeText(context,"Debes de completar todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context,"Comprueba la confirmación de la contraseña", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPassword(): Boolean {
        return password.text.toString() == confirmPassword.text.toString()
    }

    private fun checkInput(): Boolean {
        return !(nickname.text.toString() == "" ||
                name.text.toString() == "" ||
                lastName1.text.toString() == "" ||
                email.text.toString() == ""
                )
    }
}
