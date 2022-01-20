package com.example.orderit

import android.annotation.SuppressLint
import android.app.UiModeManager.MODE_NIGHT_NO
import android.content.ContentValues
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.orderit.adapters.admin_order_page
import com.example.orderit.database.Basket
import com.example.orderit.database.Order
import com.example.orderit.firebaseDataAdapter.FirebaseDataAdapter
import com.example.orderit.fragments.*
import com.example.orderit.mainViewModel.MainViewModel
import com.example.shopker.data.User
import com.example.shopker.fragments.order_page
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)



        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            if (Firebase.auth.uid == "dQ7R77yCFgZcBTyb43TwKOm9Hi53") {
                when (it.itemId) {
                    R.id.mihome -> change(home_page())
                    R.id.miproducts -> change(product_page())
                    R.id.mibasket -> change(basket_page())
                    R.id.miuser -> change(user_page())
                    R.id.miorders -> change(admin_order())

                }
            }
            else{
                when (it.itemId) {
                    R.id.mihome -> change(home_page())
                    R.id.miproducts -> change(product_page())
                    R.id.mibasket -> change(basket_page())
                    R.id.miuser -> change(user_page())
                    R.id.miorders -> change(order_page())
                }

            }
            true
        }

        val manager: FragmentManager =
            supportFragmentManager //create an instance of fragment manager
        val transaction: FragmentTransaction =
            manager.beginTransaction() //create an instance of Fragment-transaction
        transaction.replace(R.id.main_view, home_page(), "Frag_Top_tag")
        transaction.commit()

    }

    fun change(toFragment: Fragment) {
        val manager: FragmentManager =
            supportFragmentManager //create an instance of fragment manager
        val transaction: FragmentTransaction =
            manager.beginTransaction() //create an instance of Fragment-transaction
        transaction.replace(R.id.main_view, toFragment, "Frag_Top_tag")
        transaction.commit()
    }

    fun login_user(email: String, pass: String) {
        var mUserViewModel: MainViewModel
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Log in successfully", Toast.LENGTH_SHORT).show()
                mUserViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
                mUserViewModel.removeAllOrders()
                mUserViewModel.removeAllBasket()
                load_user_data(auth.uid.toString(), mUserViewModel)

                change(user_page())
            } else {
                Toast.makeText(this, "Log in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun load_user_data(uid: String, mUserViewModel: MainViewModel) {
        try {
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

            val basket_ref = Firebase.database.getReference("Users")
                .child(uid)
                .child("Basket")
                .addValueEventListener(object :
                    ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        if (snapshot.value != null && snapshot.value is HashMap<*, *>) {

                            val value = snapshot.value as HashMap<String, HashMap<String, String>>
                            Log.d(ContentValues.TAG, "Value is: " + value.isNullOrEmpty())
                            if (!value.isNullOrEmpty()) {
                                for (i in value) {
                                    val basket = Basket(
                                        i.value["uid"].toString().toInt(),
                                        i.value["name"].toString(),
                                        i.value["price"].toString().toInt(),
                                        i.value["qty"].toString().toInt(),
                                        i.value["img"].toString().toInt(),
                                    )
                                    mUserViewModel.addBasket(basket)
                                }

                            }

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                    }

                })

            Firebase.database.getReference("Users").child(uid).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val value = snapshot.value as  HashMap<String, *>
                    Log.d(ContentValues.TAG, "Value is: " + value.isNullOrEmpty())
                    if (!value.isNullOrEmpty()) {

                        val user = User(
                                0,
                                value["address"].toString(),
                                value["mobile"].toString(),
                                value["email"].toString(),
                            )
                        mUserViewModel.addUser(user)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled:$error")
                }

            })


        } catch (e: Exception) {
            Log.d("TAG", "onDataChange: ")
        }
    }

    fun register_user(
        email: String,
        pass: String,
        mobile: String,
        address: String,
        gender: String
    ) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(
            email,
            pass
        ).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                val uid = FirebaseAuth.getInstance().uid
                Toast.makeText(this, uid, Toast.LENGTH_SHORT).show()
                val reference = FirebaseDatabase.getInstance().getReference("Users")
                reference.child(uid!!).child("email").setValue(email)
                reference.child(uid).child("mobile-no").setValue(mobile)
                reference.child(uid).child("address").setValue(address)
                reference.child(uid).child("gender").setValue(gender)


////                val userPage = user_page()
//                change(userPage)
                auth.signOut()
                change(user_page())



            } else {
                Toast.makeText(
                    this,
                    "registration failed, Please try again after some time",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(
                    "error_msg",
                    "onComplete: Failed=" + Objects.requireNonNull(task.exception)!!.message
                )
            }
        }
    }


}