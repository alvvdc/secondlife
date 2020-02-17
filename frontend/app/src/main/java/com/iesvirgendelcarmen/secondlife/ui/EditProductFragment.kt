package com.iesvirgendelcarmen.secondlife.ui

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iesvirgendelcarmen.secondlife.R
import com.iesvirgendelcarmen.secondlife.model.Category
import com.iesvirgendelcarmen.secondlife.model.Product
import com.iesvirgendelcarmen.secondlife.model.ProductViewModel
import com.iesvirgendelcarmen.secondlife.model.api.Resource
import java.io.ByteArrayOutputStream
import java.io.File


class EditProductFragment(val product :Product) : Fragment(), View.OnLongClickListener {

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProviders.of(this).get(ProductViewModel::class.java)
    }

    private lateinit var titleEditText :EditText
    private lateinit var descriptionEditText :EditText
    private lateinit var priceEditText :EditText
    private lateinit var categorySpinner :Spinner
    private lateinit var save :Button
    private lateinit var loadImage :Button
    private lateinit var buttonsLayout :LinearLayout

    private val NOT_SELECTED_CATEGORY = "Categoría"
    private val IMAGE_REQUEST_CODE = 1
    private val IMAGE_QUALITY = 50 // 0 .. 100 %
    private val MAX_IMAGE_WIDTH_FOR_SCALE = 480

    private val loadedImages = mutableMapOf<Button, String>()
    private var userId = ""

    data class FormProduct (val title :String, val description :String, val price :String, val category :String)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.add_product_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViewsById(view)
        loadSpinner(view)
        fillFieldsWithExistingData()

        val mainActivity = activity as MainActivity
        if (mainActivity != null && mainActivity.isThereTokenSaved()) {

            userId = mainActivity.getSavedUserId()
            onSaveProduct()
        }

        onLoadImage()
    }

    private fun onLoadImage() {
        loadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }
    }

    private fun onSaveProduct() {
        save.setOnClickListener {

            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val price = priceEditText.text.toString()
            val category = categorySpinner.selectedItem as String

            val formProduct = FormProduct(title, description, price, category)

            val loadedImagesList = loadedImages.values.toMutableList()

            if (areFieldsFilled(formProduct)) {
                productViewModel.updateProduct(Product(product._id, userId, formProduct.title, formProduct.description, formProduct.price.toFloat(), loadedImagesList, Category.parse(formProduct.category)))
            } else {
                Toast.makeText(context, "Debes rellenar todos los campos", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {

            var bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, data?.data)
            bitmap = getScaledBitmap(bitmap, MAX_IMAGE_WIDTH_FOR_SCALE)

            val base64 = getBase64FromBitmap(bitmap)
            val path = data?.data?.path
            val file = File(path)
            val filename = file.name

            val buttonAdded = addButtonForImageAdded(filename)
            loadedImages[buttonAdded] = base64
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

    private fun addButtonForImageAdded(text :String) :Button {
        val button = Button(context, null, 0, R.style.AppTheme_ButtonCustom)
        button.text = text

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 10)
        button.layoutParams = layoutParams

        button.setOnClickListener { Toast.makeText(context, "Mantén pulsado el botón para borrar la imagen", Toast.LENGTH_SHORT).show() }

        buttonsLayout.addView(button)
        button.setOnLongClickListener(this)
        return button
    }

    override fun onLongClick(v: View?): Boolean {

        fun removeImageFromForm() {
            val iterator = loadedImages.entries.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()

                if (entry.key == v) {
                    v.visibility = View.GONE
                    iterator.remove()
                }
            }
        }

        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Borrar imagen")
        alertDialog.setMessage("¿Estás seguro de querer borrar la imagen?")
        alertDialog.setPositiveButton("SI") { dialog, which ->

            removeImageFromForm()
        }
        alertDialog.setNegativeButton("NO", null)
        alertDialog.show()
        return true
    }

    private fun areFieldsFilled(formProduct: FormProduct) :Boolean {
        if (formProduct.title.isEmpty() || formProduct.description.isEmpty() || formProduct.price.isEmpty() || formProduct.category == NOT_SELECTED_CATEGORY) {
            return false
        }
        return true
    }

    private fun findViewsById(view: View) {
        titleEditText = view.findViewById(R.id.title)
        descriptionEditText = view.findViewById(R.id.description)
        priceEditText = view.findViewById(R.id.price)
        save = view.findViewById(R.id.save)
        loadImage = view.findViewById(R.id.loadImages)
        buttonsLayout = view.findViewById(R.id.buttonsLayout)
    }

    private fun fillFieldsWithExistingData() {
        titleEditText.setText(product.title)
        descriptionEditText.setText(product.description)
        priceEditText.setText(product.price.toString())

        for (i in 0 until Category.values().size) {

            if (product.category.toString().toLowerCase() == Category.values()[i].toString().toLowerCase()) {
                categorySpinner.setSelection(i+1)
            }
        }

        var imageCount = 1
        val productImages = product.images
        for (image in productImages) {

            val newButton = addButtonForImageAdded("Imagen $imageCount")
            loadedImages[newButton] = image
            imageCount++
        }
    }

    private fun loadSpinner(view: View) {
        val categories = mutableListOf<String>()
        categories.add(NOT_SELECTED_CATEGORY)
        for (category in Category.values()) {
            categories.add(category.toString().toLowerCase().capitalize())
        }

        categorySpinner = view.findViewById(R.id.category)
        val adapter = ArrayAdapter(view.context, R.layout.spinner, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        productViewModel.productLiveData.observe(viewLifecycleOwner, Observer { resource ->

            when (resource.status) {
                Resource.Status.ERROR -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.SUCCESS -> {
                    Toast.makeText(context, "Enviado", Toast.LENGTH_SHORT).show()
                    fragmentManager?.popBackStack()
                }
            }
        })
    }
}