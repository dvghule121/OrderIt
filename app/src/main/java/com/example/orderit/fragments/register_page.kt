package com.example.orderit.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.orderit.R
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import android.widget.RadioButton

import android.widget.RadioGroup
import android.widget.Toast

import com.example.orderit.MainActivity







// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [register_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class register_page : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var auth: FirebaseAuth? = null

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
        var gender = ""
        val view = inflater.inflate(R.layout.fragment_register_page, container, false)
        val username = view.findViewById<EditText>(R.id.user_name_et)
        val usermobile = view.findViewById<EditText>(R.id.user_mobile_et)
        val useraddress = view.findViewById<EditText>(R.id.user_address_et)
        val usergender = view.findViewById<RadioGroup>(R.id.gender)




        val password = view.findViewById<EditText>(R.id.user_pass_et)
        val createAccount = view.findViewById<Button>(R.id.create_button)

        createAccount.setOnClickListener {
            auth = FirebaseAuth.getInstance()

            // Add the Listener to the RadioGroup
            // Add the Listener to the RadioGroup
            usergender.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked
                    // Check which radio button has been clicked
                    // Get the selected Radio Button
                    val radioButton = group
                        .findViewById<View>(checkedId) as RadioButton
                })
            // When submit button is clicked,
            // Ge the Radio Button which is set
            // If no Radio Button is set, -1 will be returned
            // When submit button is clicked,
            // Ge the Radio Button which is set
            // If no Radio Button is set, -1 will be returned


            val selectedId: Int = usergender.checkedRadioButtonId
            if (selectedId == -1) {
                true
            }
            else {
                // Now display the value of selected item
                // by the Toast message
                gender = usergender.findViewById<RadioButton>(selectedId).text.toString()
            }
            if (TextUtils.isEmpty(username.toString()) || TextUtils.isEmpty(password.toString()) || gender == "") {
                Toast.makeText(
                    context,
                    "Please enter email id and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                val activity = activity as MainActivity
                activity.register_user(username.text.toString(),password.text.toString(),usermobile.text.toString(),useraddress.text.toString(),gender)
                Toast.makeText(
                    context,
                    "successfully registered",
                    Toast.LENGTH_SHORT
                ).show()
            }


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
         * @return A new instance of fragment register_page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            register_page().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }




}

