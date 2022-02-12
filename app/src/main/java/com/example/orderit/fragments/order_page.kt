package com.example.shopker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.MainActivity
import com.example.orderit.R
import com.example.orderit.adapters.Admin_order_adapter
import com.example.orderit.adapters.OrderListAdapter
import com.example.orderit.adapters.admin_order_page
import com.example.orderit.database.Order
import com.example.orderit.firebaseDataAdapter.FirebaseDataAdapter
import com.example.orderit.fragments.admin_order
import com.example.orderit.fragments.login_page
import com.example.orderit.mainViewModel.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [order_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class order_page : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mUserViewModel: MainViewModel

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

        val view = inflater.inflate(R.layout.fragment_order_page, container, false)
        mUserViewModel = ViewModelProvider(this).get(MainViewModel::class.java)



        val order_list = view.findViewById<RecyclerView>(R.id.order_view)
        val adapter = OrderListAdapter()
        order_list.adapter = adapter
        FirebaseDataAdapter().load_user_data(mUserViewModel, Firebase.auth.uid.toString())
        mUserViewModel.orderList.observe(viewLifecycleOwner, Observer { order ->
            if (order.isEmpty()){
                view.findViewById<ImageView>(R.id.no_item).setImageResource(R.drawable.no_product_img)
                view.findViewById<TextView>(R.id.noItem).text = "No item ordered yet !"
            }
            else {

                val tempListdelivered = mutableListOf<Order>()
                val tempListpending = mutableListOf<Order>()

                for (i in order) {
                    if (i.status == "delivered") {
                        tempListdelivered.add(i)
                    } else {
                        tempListpending.add(i)
                    }

                }
                adapter.setData(tempListpending)

                val btn =
                    view.findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.switch_status)
                btn.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        adapter.setData(tempListdelivered)
                    } else {
                        adapter.setData(tempListpending)
                    }
                })
            }
            

        })
        order_list.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL, false
        )

        return view
    }

    override fun onStart() {
        super.onStart()

        val auth = Firebase.auth

        val uid = auth.currentUser?.uid
        if (uid.isNullOrBlank()){
            val activity: MainActivity = activity as MainActivity
            val loginPage = login_page()
            activity.change(loginPage)
            Log.d("clicked", "clicked")
        }
        else{
            true

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment order_card.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            order_page().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}