package com.iesvirgendelcarmen.secondlife.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iesvirgendelcarmen.secondlife.R

class ProductRecyclerViewAdapter(var productsList :List<Product>) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>(), Filterable {

    lateinit var lista : List<Product>

    override fun getItemCount() = productsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_element, parent, false)

        lista = productsList.toList()

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
            productDescription.text = product.description

            Double
            productPrice.text = "${product.price.toInt()}€"

            if (product.images.size > 0) {
                Glide
                    .with(itemView)
                    .load(product.images[0])
                    .into(productImage)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                productsList = results?.values as List<Product>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint?.toString()?.toLowerCase()
                val filterResults = Filter.FilterResults()

                filterResults.values =
                    if (queryString == null || queryString.isEmpty()) {
                        lista
                    } else {
                        lista.filter {
                            it.title.toLowerCase().contains(queryString) || it.description.toLowerCase().contains(queryString)
                        }
                    }
                return filterResults
            }
        }
    }
}