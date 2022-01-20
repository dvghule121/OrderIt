package com.example.orderit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.R
import com.example.orderit.firebaseDataAdapter.FirebaseDataAdapter
import com.example.orderit.database.Order
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Admin_order_adapter() : RecyclerView.Adapter<Admin_order_ViewHolder>(),
    AdapterView.OnItemSelectedListener {
    var orders = emptyList<Order>()
    var st = "ordered"
    var t = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Admin_order_ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.admin_order_card, parent, false)
        return Admin_order_ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Admin_order_ViewHolder, position: Int) {
        val orderItem = orders[position]
        holder.textname.text = orderItem.name
        holder.textemail.text = orderItem.email
        ("Deliver to: "+ orderItem.address).also { holder.textaddress.text = it }
        holder.textprice.text =
            (orderItem.qty?.let { orderItem.price?.times(it?.toInt()) }).toString()
        holder.order_id.text = orderItem.uid.toString()
        holder.order_date.text = orderItem.date
        holder.textqtty.text = orderItem.qty.toString()
        holder.img.setImageResource(orderItem.img!!)
        holder.txtstatus.text = orderItem.status
        val fbmanager = FirebaseDataAdapter()



        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            holder.img.context,
            R.array.order_status,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            holder.textstatus.adapter = adapter
        }

        when(orderItem.status){
            "Ordered" -> t=0
            "shipped" -> t=1
            "on the way" -> t=2
            "out for delivery"-> t=3
            "delivered" -> t=4
            "ordered" -> t=0
        }



        holder.textstatus.onItemSelectedListener = this

        holder.commit.setOnClickListener {
            fbmanager.change_order_status(st.toString(), orderItem.uid)
        }
        holder.textstatus.setSelection(t)
    }

    fun setData(order: List<Order>) {
        this.orders = order
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        if (parent != null) {
            st = parent.getItemAtPosition(position).toString()
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Another interface callback
        if (parent != null) {
            Toast.makeText(parent.context, st+" selected", Toast.LENGTH_SHORT).show()
        }

    }
}

class Admin_order_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textname = itemView.findViewById<TextView>(R.id.basket_name)
    val textprice = itemView.findViewById<TextView>(R.id.basket_price)
    val img = itemView.findViewById<ImageView>(R.id.basket_img)
    val textqtty = itemView.findViewById<TextView>(R.id.order_qtty)
    var textstatus = itemView.findViewById<Spinner>(R.id.spinner)
    var txtstatus= itemView.findViewById<TextView>(R.id.txt_status)
    val order_id = itemView.findViewById<TextView>(R.id.order_id)
    val order_date = itemView.findViewById<TextView>(R.id.order_date)
    val commit = itemView.findViewById<Button>(R.id.checkout_btn)
    val textemail = itemView.findViewById<TextView>(R.id.user_email)
    val textaddress = itemView.findViewById<TextView>(R.id.address_user)
    val textmobile = itemView.findViewById<TextView>(R.id.user_mobile)


}


