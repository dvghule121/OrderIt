package com.example.orderit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
 * Use the [best_sellers.newInstance] factory method to
 * create an instance of this fragment.
 */
class best_sellers : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var mUserViewModel: MainViewModel

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
        val view = inflater.inflate(R.layout.fragment_best_sellers, container, false)
        val orders = mutableListOf<ProductItem>()
        orders.add(ProductItem("Dominoes Extra large mushroom pizza", 249, 4, R.drawable.pizza))
        orders.add(ProductItem("Coffee", 99, 4, R.drawable.coffe))
        orders.add(ProductItem("Burger", 79, 5, R.drawable.burger))
        orders.add(ProductItem("Vada pav ", 79, 5, R.drawable.burger))
        orders.add(ProductItem("Ginger chai", 79, 5, R.drawable.burger))
        val order_list = view.findViewById<RecyclerView>(R.id.bs_list)
        mUserViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)



        order_list.adapter = BsListAdapter(mUserViewModel, orders)
        order_list.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL, false
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
         * @return A new instance of fragment best_sellers.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            best_sellers().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}