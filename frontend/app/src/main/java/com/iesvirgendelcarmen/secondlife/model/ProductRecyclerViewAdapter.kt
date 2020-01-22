package com.iesvirgendelcarmen.secondlife.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iesvirgendelcarmen.secondlife.R

class ProductRecyclerViewAdapter(var productsList :List<Product>) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>() {

    override fun getItemCount() = productsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_element, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productsList[position]
        holder.bind(product)
    }

    inner class ProductViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {

        private val productImage = itemView.findViewById<ImageView>(R.id.productImage)
        private val productTitle = itemView.findViewById<TextView>(R.id.productTitle)
        private val productDescription = itemView.findViewById<TextView>(R.id.productDescription)
        private val productPrice = itemView.findViewById<TextView>(R.id.productPrice)

        fun bind(product :Product) {
            productTitle.text = product.title

            if (product.description.isEmpty())
                productDescription.visibility = View.GONE
            else
                productDescription.text = product.description


            Double
            productPrice.text = "${product.price.toInt()}€"

            Glide
                .with(itemView)
                .load(product.images[0])
                .into(productImage)
        }
    }
}