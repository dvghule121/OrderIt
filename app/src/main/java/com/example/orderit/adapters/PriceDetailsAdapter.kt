package com.example.orderit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.R
import com.example.orderit.database.Basket
import com.example.orderit.database.OrderSummaryItem


class PriceDetailsAdapter() : RecyclerView.Adapter<PriceDetailsViewHolder>() {
    var orders = mutableListOf<OrderSummaryItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceDetailsViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.price_details, parent, false)
        return PriceDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PriceDetailsViewHolder, position: Int) {
        val order = orders[position]
        holder.textTitle.text = order.name.toString()
        holder.prqty.text = order.qtty.toString()
        ("₹" + order.price.toString()).also { holder.textdiscription.text = it }

    }

    fun setData(basket: List<OrderSummaryItem>) {
        this.orders = basket as MutableList<OrderSummaryItem>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}

class PriceDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textTitle = itemView.findViewById<TextView>(R.id.pr_detail_name)
    val prqty = itemView.findViewById<TextView>(R.id.qty)
    val textdiscription = itemView.findViewById<TextView>(R.id.pr_detail_price)


}


