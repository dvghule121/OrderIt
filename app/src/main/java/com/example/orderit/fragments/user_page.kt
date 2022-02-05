package com.example.orderit.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.orderit.MainActivity
import com.example.orderit.R
import com.example.orderit.mainViewModel.MainViewModel
import com.example.shopker.fragments.order_page
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [user_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class user_page : Fragment() {
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
        val auth = Firebase.auth
        val view =  inflater.inflate(R.layout.fragment_user_page, container, false)
        val username = view.findViewById<TextView>(R.id.username)
        val address = view.findViewById<TextView>(R.id.mobile_no)
        val basket = view.findViewById<Button>(R.id.basket)
        val orders = view.findViewById<Button>(R.id.orders)
        val main_menu = view.findViewById<Button>(R.id.main_menu)
        val logout= view.findViewById<Button>(R.id.logout)

        try {
            Firebase.database.reference.child("Users").child(auth.currentUser!!.uid)
                .child("address").addValueEventListener(object :
                ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = snapshot.getValue<String>()
                    Log.d(TAG, "Value is: " + value)
                    address.text = value
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }

            })

            Firebase.database.reference.child("Users").child(auth.currentUser!!.uid)
                .child("email").addValueEventListener(object :
                    ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        val value = snapshot.getValue<String>()
                        Log.d(TAG, "Value is: " + value)
                        username.text = value
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(TAG, "Failed to read value.", error.toException())
                    }

                })
        }catch (e:Exception){
            true

        }

        basket.setOnClickListener{
            val activity = activity as MainActivity
            activity.change(basket_page())
        }

        orders.setOnClickListener{
            val activity = activity as MainActivity
            activity.change(order_page())
        }
        main_menu.setOnClickListener{
            val activity = activity as MainActivity
            activity.change(home_page())
        }



        logout.setOnClickListener{
            auth.signOut()
            val mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            val activity:MainActivity = activity as MainActivity
            mMainViewModel.removeUser()
            mMainViewModel.removeAllOrders()
            mMainViewModel.removeAllBasket()
            activity.change(login_page())


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
         * @return A new instance of fragment user_page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            user_page().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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


}