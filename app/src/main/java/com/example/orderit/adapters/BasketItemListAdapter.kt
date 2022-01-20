package com.example.orderit.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.mainViewModel.MainViewModel
import com.example.orderit.R
import com.example.orderit.database.Basket


class BasketItemListAdapter(val muserViewModel: MainViewModel) :
    RecyclerView.Adapter<BasketItemListViewHolder>() {
    var orders: List<Basket> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemListViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.basket_card, parent, false)
        return BasketItemListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketItemListViewHolder, position: Int) {
        val basketItem = orders[position]
        val id: Int = basketItem.uid
        holder.textTitle.text = basketItem.name
        holder.textdiscription.text = basketItem.price.toString()
        basketItem.img?.let { holder.img.setImageResource(it) }
        holder.textqtty.text = basketItem.qty.toString()
        holder.addbtn.setOnClickListener {
            var curQty = holder.textqtty.text.toString()

            curQty = (curQty.toInt() + 1).toString()
            muserViewModel.updateBasket(
                Basket(
                    id,
                    basketItem.name,
                    basketItem.price,
                    curQty.toInt(),
                    basketItem.img
                )
            )
        }

        holder.subbtn.setOnClickListener {
            var curQty = holder.textqtty.text.toString()
            if (curQty.toInt() <= 1) {
                muserViewModel.removeBasket(
                    Basket(
                        id,
                        basketItem.name,
                        basketItem.price,
                        basketItem.qty,
                        basketItem.img
                    )
                )
                Toast.makeText(
                    holder.img.context,
                    "removed " + basketItem.name + " from basket",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                curQty = (curQty.toInt() - 1).toString()
                muserViewModel.updateBasket(
                    Basket(
                        id,
                        basketItem.name,
                        basketItem.price,
                        curQty.toInt() ,
                        basketItem.img
                    )
                )


            }

        }

        holder.removebtn.setOnClickListener {
            muserViewModel.removeBasket(
                Basket(
                    id,
                    basketItem.name,
                    basketItem.price,
                    basketItem.qty,
                    basketItem.img
                )
            )
        }
    }

    fun setData(basket: List<Basket>) {
        this.orders = basket
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return orders.size
    }


}

class BasketItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textTitle = itemView.findViewById<TextView>(R.id.basket_name)
    val textqtty = itemView.findViewById<TextView>(R.id.basket_qtty)
    val textdiscription = itemView.findViewById<TextView>(R.id.basket_price)
    val img = itemView.findViewById<ImageView>(R.id.basket_img)
    val addbtn = itemView.findViewById<Button>(R.id.addqtty)
    val subbtn = itemView.findViewById<Button>(R.id.subqtty)
    val removebtn = itemView.findViewById<Button>(R.id.remove_item)


}


