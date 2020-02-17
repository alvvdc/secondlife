package com.iesvirgendelcarmen.secondlife.ui

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.iesvirgendelcarmen.secondlife.model.User
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.UserViewModel
import com.iesvirgendelcarmen.secondlife.model.api.Resource
import java.io.ByteArrayOutputStream
import java.io.File


class ProfileFragment(val sharedPreferences: SharedPreferences): Fragment() {

    lateinit var nickname: EditText
    lateinit var name: EditText
    lateinit var lastName1: EditText
    lateinit var lastName2: EditText
    lateinit var phone: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var confirmPassword: EditText
    lateinit var imageView: ImageView
    lateinit var loadImage: Button
    lateinit var removeImage: Button

    private var base64Image = ""

    private val IMAGE_REQUEST_CODE = 1
    private val MAX_IMAGE_WIDTH_FOR_SCALE = 480
    private val IMAGE_QUALITY = 50 // 0 .. 100 %

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
        imageView = view.findViewById(R.id.image)
        loadImage = view.findViewById(R.id.loadImage)

        val saveButton = view.findViewById<Button>(R.id.save)
        val deleteAccountButton = view.findViewById<Button>(R.id.deleteAccount)

        val userID = sharedPreferences.getString("userID", "null")!!
        val token = sharedPreferences.getString("token", "null")!!

        var type = 0

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
                    base64Image = resource.data.image

                    val decoded = Base64.decode(resource.data.image, Base64.NO_WRAP)
                    val bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)

                    Glide
                        .with(imageView)
                        .load(bitmap)
                        .into(imageView)

                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, "Error al cargar tus datos", Toast.LENGTH_SHORT).show()
                }
            }
        })

        saveButton.setOnClickListener { saveUser(userID, type, token) }

        loadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        deleteAccountButton.setOnClickListener {
            delete(userID, token)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {

            var bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, data?.data)
            bitmap = getScaledBitmap(bitmap, MAX_IMAGE_WIDTH_FOR_SCALE)

            val base64 = getBase64FromBitmap(bitmap)
            val path = data?.data?.path
            val file = File(path)
            val filename = file.name

            base64Image = base64
            imageView.setImageBitmap(bitmap)
            loadImage.setText(filename)

        }
    }

    private fun getBase64FromBitmap(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    private fun getScaledBitmap(bitmap: Bitmap, width :Int): Bitmap {
        var scaledBitmap = bitmap

        if (scaledBitmap.width > 480) {
            val aspectRatio: Float = scaledBitmap.width.toFloat() / scaledBitmap.height.toFloat()
            val height = (width / aspectRatio)
            scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap, width, height.toInt(), false)
        }

        return scaledBitmap
    }

    private fun delete(userID: String, token: String) {
        AlertDialog.Builder(context!!)
            .setTitle("Borrar cuenta")
            .setMessage("Se va a borrar tu cuenta y tus datos permanentemente, no la podrás volver a recuperar, ¿Deseas continuar?")
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which ->

                userViewModel.deleteUser(userID, token)

                userViewModel.deleteLiveData.observe(viewLifecycleOwner, Observer { resource ->


                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            (activity as MainActivity).logout()

                            var activity = (activity as MainActivity)
                            activity.changeHeaderData()
                            activity.showProductsListFragment()

                        }
                        Resource.Status.ERROR -> {
                            Toast.makeText(
                                context,
                                "Error al borrar tu usuario",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }

            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.stat_sys_warning)
            .show()
    }

    private fun saveUser(userID: String, type: Int, token: String) {

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
                    base64Image
                )
                userViewModel.editUser(user, token)

                userViewModel.userLiveData.observe(viewLifecycleOwner, Observer { resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            Toast.makeText(context,"Usuario actualizado con exito", Toast.LENGTH_SHORT).show()
                            (activity as MainActivity).changeHeaderData()
                            (activity as MainActivity).showProfile()
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
