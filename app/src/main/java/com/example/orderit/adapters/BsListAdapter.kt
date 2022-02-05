package com.example.orderit.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.R
import com.example.orderit.database.Basket
import com.example.orderit.database.ProductItem
import com.example.orderit.mainViewModel.MainViewModel
import kotlin.random.Random

class BsListAdapter(var mUserViewModel: MainViewModel, val orders: List<ProductItem>) :
    RecyclerView.Adapter<BsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BsListViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.best_seller_card, parent, false)
        return BsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BsListViewHolder, position: Int) {
        val productItem = orders[position]
        holder.textTitle.text = productItem.name
        holder.textPrice.text = productItem.price.toString()
        holder.rating.text = "★".repeat(productItem.rating)
        holder.img_bs.setImageResource(productItem.imgId)
        holder.btn.setOnClickListener {
            val basket = Basket(
                Random.nextInt(10000),
                productItem.name,
                productItem.price,
                1,
                productItem.imgId
            )


            mUserViewModel.addBasket(
                basket
            )
            Toast.makeText(
                holder.img_bs.context,
                productItem.name + " is added to basket",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("btn_status", mUserViewModel.basketList.toString())
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }


}

class BsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textTitle = itemView.findViewById<TextView>(R.id.bs_name)
    val textPrice = itemView.findViewById<TextView>(R.id.bs_price)
    val img_bs = itemView.findViewById<ImageView>(R.id.img_pr)
    val rating = itemView.findViewById<TextView>(R.id.bs_rating)
    val btn = itemView.findViewById<Button>(R.id.add_to_basket)


}


