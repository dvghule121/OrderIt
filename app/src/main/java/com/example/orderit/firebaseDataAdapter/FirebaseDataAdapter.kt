package com.example.orderit.firebaseDataAdapter

import android.content.ContentValues
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.example.orderit.R
import com.example.orderit.database.Basket
import com.example.orderit.database.Order
import com.example.orderit.mainViewModel.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.HashMap


class FirebaseDataAdapter() {
    val uid = Firebase.auth.uid.toString()
    val database = Firebase.database.getReference("Users")

    suspend fun addBasket(basket: Basket) {
        database.child(uid).child("Basket").child(basket.uid.toString()).setValue(basket)

    }

    suspend fun updateBasket(basket: Basket){
        database.child(uid).child("Basket").child(basket.uid.toString()).child("qty").setValue(basket.qty.toString())
    }

    suspend fun addOrder(order: Order) {
        database.child("Orders").child(order.uid.toString()).setValue(order)
        database.child(uid).child("Orders").push().setValue(order.uid.toString())

    }

    suspend fun removeBasket(basket_uid: String) {
        database.child(uid).child("Basket").child(basket_uid).setValue(null)
    }

    fun changeAddress(address: String) {
        database.child(uid).child("address").setValue(address)
    }

    fun load_order_data(mUserViewModel: MainViewModel) {
        var list = mutableListOf<Order>()
        Firebase.database.reference.child("Users").child("Orders").addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (snapshot.value != null) {
                    val orderList = mutableListOf<Order>()

                    val value = snapshot.value as HashMap<String, HashMap<String, String>>
                    Log.d(ContentValues.TAG, "Value is: " + value.isNullOrEmpty())
                    if (!value.isNullOrEmpty()) {
                        for (i in value) {
                            val order = Order(
                                i.value["uid"].toString().toInt(),
                                i.value["name"].toString(),
                                i.value["address"].toString(),
                                i.value["email"].toString(),
                                i.value["payment"].toString(),
                                i.value["price"].toString().toInt(),
                                i.value["img"].toString().toInt(),
                                i.value["date"].toString(),
                                i.value["status"].toString(),
                                i.value["qty"].toString().toInt()



                            )
                            orderList.add(order)

                        }
                        mUserViewModel.removeAllOrders()
                        mUserViewModel.addOrder(orderList)



                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }

        })
        Log.d("list", list.toString())

    }

    fun load_user_order(mUserViewModel: MainViewModel, order_uid:String){
        Firebase.database.reference.child("Users").child("Orders").child(order_uid).addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (snapshot.value != null) {
                    val orderList = mutableListOf<Order>()

                    val value = snapshot.value as HashMap<String, String>
                    Log.d(ContentValues.TAG, "Value is: " + value.isNullOrEmpty())
                    if (!value.isNullOrEmpty()) {

                        val order = Order(
                            value["uid"].toString().toInt(),
                            value["name"].toString(),
                            value["address"].toString(),
                            value["email"].toString(),
                            value["payment"].toString(),
                            value["price"].toString().toInt(),
                            value["img"].toString().toInt(),
                            value["date"].toString(),
                            value["status"].toString(),
                            value["qty"].toString().toInt()

                        )
                        mUserViewModel.adduserOrder(order)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }

        })

    }

    fun load_user_data(mUserViewModel: MainViewModel, uid:String){
        val order_ref = Firebase.database.getReference("Users")
            .child(uid)
            .child("Orders")
            .addValueEventListener(object :
                ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (snapshot.value != null) {

                        val value = snapshot.value as HashMap<String, String>
                        Log.d(ContentValues.TAG, "Value is: " + value.isNullOrEmpty())
                        if (!value.isNullOrEmpty()) {
                            for (i in value) {
                                val order = i.value.toString()
                                FirebaseDataAdapter().load_user_order(mUserViewModel,order)

                            }

                        }


                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }

            })
    }

    fun change_order_status(status: String, orderId: Int) {
        val order_ref = Firebase.database.getReference("Users")
            .child("Orders").child(orderId.toString()).child("status").setValue(status)
    }




}