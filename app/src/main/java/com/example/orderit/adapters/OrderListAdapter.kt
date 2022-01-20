package com.example.orderit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.R
import com.example.orderit.database.Order


class OrderListAdapter() : RecyclerView.Adapter<OrderListViewHolder>() {
    var orders = emptyList<Order>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.order_card, parent, false)
        return OrderListViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val orderItem = orders[position]
        holder.textname.text = orderItem.name
        holder.textprice.text =
            (orderItem.qty?.let { orderItem.price?.times(it?.toInt()) }).toString()
        holder.order_id.text = orderItem.uid.toString()
        holder.order_date.text = orderItem.date
        holder.textqtty.text = orderItem.qty.toString()
        holder.img.setImageResource(orderItem.img!!)
        holder.textstatus.text = orderItem.status
    }

    fun setData(order: List<Order>) {
        this.orders = order
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}

class OrderListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textname = itemView.findViewById<TextView>(R.id.basket_name)
    val textprice = itemView.findViewById<TextView>(R.id.basket_price)
    val img = itemView.findViewById<ImageView>(R.id.basket_img)
    val textqtty = itemView.findViewById<TextView>(R.id.order_qtty)
    val textstatus = itemView.findViewById<TextView>(R.id.order_status)
    val order_id = itemView.findViewById<TextView>(R.id.order_id)
    val order_date = itemView.findViewById<TextView>(R.id.order_date)


}


