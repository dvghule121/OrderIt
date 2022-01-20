package com.example.orderit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.R
import com.example.orderit.adapters.Admin_order_adapter
import com.example.orderit.adapters.OrderListAdapter
import com.example.orderit.database.Order
import com.example.orderit.firebaseDataAdapter.FirebaseDataAdapter
import com.example.orderit.mainViewModel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [admin_order.newInstance] factory method to
 * create an instance of this fragment.
 */
class admin_order : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_admin_order_page, container, false)
        mUserViewModel = ViewModelProvider(this).get(MainViewModel::class.java)



        val order_list = view.findViewById<RecyclerView>(R.id.admin_order)
        val adapter = Admin_order_adapter()
        order_list.adapter = adapter
        val fbmanager = FirebaseDataAdapter()
        fbmanager.load_order_data(mUserViewModel)
        mUserViewModel.orderList.observe(viewLifecycleOwner, Observer { order ->
            val tempListdelivered = mutableListOf<Order>()
            val tempListpending = mutableListOf<Order>()

            for (i in order){
                if (i.status == "delivered"){
                    tempListdelivered.add(i)
                }
                else{
                    tempListpending.add(i)
                }

            }
            adapter.setData(tempListpending)

            val btn = view.findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.switch_status)
            btn.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    adapter.setData(tempListdelivered)
                }
                else {
                    adapter.setData(tempListpending)
                }
            })


        })
        order_list.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL, false
        )


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment admin_order.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            admin_order().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}