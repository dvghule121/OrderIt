package com.example.orderit.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.R


class ProductListAdapter(val orders: List<String>) : RecyclerView.Adapter<ProductListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.best_seller_card, parent, false)
        return ProductListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.textTitle.text = orders[position]
    }

    override fun getItemCount(): Int {
        return orders.size
    }


}

class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textTitle = itemView.findViewById<TextView>(R.id.bs_name)
    val textdiscription = itemView.findViewById<TextView>(R.id.bs_price)


}


