package com.example.orderit.fragments

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.orderit.MainActivity
import com.example.orderit.R
import com.example.orderit.R.*
import com.example.orderit.database.Order
import com.example.orderit.database.OrderSummaryItem
import com.example.orderit.mainViewModel.MainViewModel
import com.example.shopker.fragments.order_page
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Buy_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class Buy_page : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(layout.fragment_buy_page, container, false)
        val mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val manager: FragmentManager =
            requireActivity().supportFragmentManager //create an instance of fragment manager
        val transaction: FragmentTransaction =
            manager.beginTransaction() //create an instance of Fragment-transaction

        val bundle = this.arguments
        if (bundle != null) {

            val price_summery = basket_price_page()
            price_summery.arguments = bundle

            transaction.add(R.id.order_summery, price_summery, "Frag_Top_tag")
            transaction.commit()

        }





        view.findViewById<Button>(R.id.buy_btn).setOnClickListener {
            mMainViewModel.repository.userList.observe(viewLifecycleOwner, { user ->


                val address = user[0].address
                val email = user[0].email
                val a_list = bundle!!.getParcelableArrayList<OrderSummaryItem>("pre_order_list")!!
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val date = sdf.format(Date())


                var payment = ""
                val payment_opt = view.findViewById<RadioGroup>(R.id.payment_opt)
                val selectedId: Int = payment_opt.checkedRadioButtonId

                if (selectedId == -1) {
                    Toast.makeText(view.context, "Please choose payment option", Toast.LENGTH_SHORT).show()

                }
                else{
                    val choice = view.findViewById(selectedId) as RadioButton
                    payment = choice.text.toString()

                    for (i in a_list) {
                        val order = Order(
                            Random().nextInt(10000),
                            i.name,
                            address.toString(),
                            email,
                            payment,
                            i.price,
                            i.img,
                            date.toString(),
                            "Ordered",
                            i.qtty

                        )
                        mMainViewModel.addOrder(order)
                        mMainViewModel.basketList.observe(viewLifecycleOwner, { basket ->
                            for (i in basket) {
                                mMainViewModel.removeBasket(i)
                            }
                            val a = activity as MainActivity
                            a.change(order_page())
                        })
                        Toast.makeText(context, "order placed successfully", Toast.LENGTH_SHORT)
                            .show()

                    }

                }
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
         * @return A new instance of fragment Buy_page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Buy_page().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}