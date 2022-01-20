package com.example.orderit.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.MainActivity
import com.example.orderit.R
import com.example.orderit.adapters.BasketItemListAdapter
import com.example.orderit.database.OrderSummaryItem
import com.example.orderit.mainViewModel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [basket_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class basket_page : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_basket_page, container, false)
        mUserViewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        val adapter = BasketItemListAdapter(mUserViewModel)
        val order_list = view.findViewById<RecyclerView>(R.id.basket_view)
        val pre_order = ArrayList<OrderSummaryItem>()
        order_list.adapter = adapter
        order_list.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL, false
        )

        order_list.adapter = adapter
        mUserViewModel.basketList.observe(viewLifecycleOwner, Observer { basket ->
            adapter.setData(basket)

        })



        view.findViewById<Button>(R.id.place_order).setOnClickListener {
            mUserViewModel.basketList.observe(viewLifecycleOwner, { basket ->

                val BasketList = basket
                adapter.setData(basket)
                val pre_order = ArrayList<OrderSummaryItem>()
                for (i in basket) {
                        val order = OrderSummaryItem(
                            i.name,
                            i.price.toString().toInt(),
                            i.img,
                            i.qty.toString().toInt()
                        )
                        pre_order.add(order)
                }



                    val bundle = Bundle();
                    bundle.putParcelableArrayList(
                        "pre_order_list",
                        pre_order
                    ); // Put anything what you want

                    val fragment2 = Buy_page();
                    fragment2.setArguments(bundle);

                    val act = activity as MainActivity
                    act.change(fragment2)






            })


        }
        return view

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment basket_page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            basket_page().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}




