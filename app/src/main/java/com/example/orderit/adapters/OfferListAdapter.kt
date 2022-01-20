package com.example.orderit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.R
import com.example.orderit.database.OfferItem

class OfferListAdapter(val orders: List<OfferItem>) : RecyclerView.Adapter<OfferListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferListViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.offer_card, parent, false)
        return OfferListViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferListViewHolder, position: Int) {
        val order = orders[position]
        holder.textTitle.text = order.title
        holder.textdiscription.text = order.description
        holder.offerImg.setImageResource(order.imgId)
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}

class OfferListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textTitle = itemView.findViewById<TextView>(R.id.texttitle)
    val textdiscription = itemView.findViewById<TextView>(R.id.textdiscription)
    val offerImg = itemView.findViewById<ImageView>(R.id.offer_img)


}


