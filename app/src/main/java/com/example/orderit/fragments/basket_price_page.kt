package com.example.orderit.fragments

import android.app.Dialog
import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.R
import com.example.orderit.adapters.PriceDetailsAdapter
import com.example.orderit.database.Order
import com.example.orderit.database.OrderSummaryItem
import com.example.orderit.firebaseDataAdapter.FirebaseDataAdapter
import com.example.orderit.mainViewModel.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [basket_price_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class basket_price_page : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mUserViewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_basket_price_page, container, false)
        mUserViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val show_list = view.findViewById<RecyclerView>(R.id.pr_details)
        val adapter = PriceDetailsAdapter()
        show_list.adapter = adapter
        show_list.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        val bundle = this.arguments
        val a_list = bundle!!.getParcelableArrayList<OrderSummaryItem>("pre_order_list")!!
        adapter.setData(a_list)
        Log.d("TAG", a_list[0].toString())


        Firebase.database.reference.child("Users").child(Firebase.auth.uid.toString()).child("address").addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (snapshot.value != null) {
                    val orderList = mutableListOf<Order>()

                    val value = snapshot.value as  String
                    Log.d(ContentValues.TAG, "Value is: " + value.isNullOrEmpty())
                    view.findViewById<TextView>(R.id.address).text = value
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }

        })


        val dialog = Dialog(view.context)
        dialog.setContentView(R.layout.change_address)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        }
        dialog.getWindow()?.setLayout(1000, 800);
        dialog.setCancelable(false); //Optional
        dialog.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog


        val et = dialog.findViewById<EditText>(R.id.address_text)
        val change_btn = dialog.findViewById<Button>(R.id.change)

        dialog.findViewById<Button>(R.id.cancel_btn).setOnClickListener {
            dialog.cancel()
        }

        change_btn.setOnClickListener {
            FirebaseDataAdapter().changeAddress(address = et.text.toString())
            dialog.cancel()
        }

        view.findViewById<Button>(R.id.change_address).setOnClickListener {
            dialog.show()
        }

            mUserViewModel.basketList.observe(viewLifecycleOwner, Observer { basket ->
                var total: Int = 0
                var BasketList = basket
                if (BasketList != null) {
                    for (i in BasketList) {
                        total += i.price!!*i.qty!!
                    }
                }

                val total_text = view.findViewById<TextView>(R.id.bs_total)
                total_text.text = "₹" + total.toString()

            })


        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment basket_price_page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            basket_price_page().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}