package com.example.orderit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderit.MainActivity
import com.example.orderit.R
import com.example.orderit.adapters.BsListAdapter
import com.example.orderit.database.ProductItem
import com.example.orderit.mainViewModel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [product_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class product_page : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_product_page, container, false)
        val orders = mutableListOf<ProductItem>()
        orders.add(ProductItem("Dominoes Extra large mushroom pizza", 199, 4, R.drawable.pizza))
        orders.add(ProductItem("Ginger chai", 15, 4, R.drawable.chai))
        orders.add(ProductItem("Special maharashtrian kanda Poha", 50, 4, R.drawable.poha))
        orders.add(ProductItem("Vada pav", 15, 4, R.drawable.vada_pav))
        orders.add(ProductItem("Idli sambhar", 50, 4, R.drawable.idli))
        orders.add(ProductItem("Vada sambhar", 50, 4, R.drawable.vada_sambar))
        orders.add(ProductItem("Special punjabi aloo ke parothe", 70, 4, R.drawable.aloo_paratha))
        orders.add(ProductItem("french fries", 50, 4, R.drawable.french_fries))
        orders.add(ProductItem("Coffee", 50, 4, R.drawable.coffe))
        orders.add(ProductItem("Burger", 79, 5, R.drawable.burger))
        orders.add(ProductItem("Special Cold coffee", 99, 5, R.drawable.cold_coffee))
        orders.add(ProductItem("Cheese sandwich", 120, 3, R.drawable.sandwich))
        orders.add(ProductItem("Tomato soup", 60, 5, R.drawable.tomato_soup))
        val order_list = view.findViewById<RecyclerView>(R.id.product_list)
        mUserViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        order_list.adapter = BsListAdapter(mUserViewModel, orders)
        order_list.layoutManager = GridLayoutManager(view.context, 2)

        val searchbar = view.findViewById<SearchView>(R.id.search_bar)
        searchbar.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchbar.clearFocus()
                val new_orders = mutableListOf<ProductItem>()
                for(i in orders){
                    if (query.toString() in i.name.lowercase()){
                        new_orders.add(i)
                    }
                }
                order_list.adapter = BsListAdapter(mUserViewModel, new_orders)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val new_orders = mutableListOf<ProductItem>()
                for(i in orders){
                    if (newText.toString() in i.name.lowercase()){
                        new_orders.add(i)
                    }
                }
                order_list.adapter = BsListAdapter(mUserViewModel, new_orders)
                return false
            }
        } )

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment product_page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            product_page().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}