package com.bitcodetech.webservices2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.bitcodetech.webservices2.models.UserPostModel
import com.google.gson.Gson
import org.json.JSONObject

class AddUserFragment : Fragment() {

    private lateinit var edtName : EditText
    private lateinit var edtJob : EditText
    private lateinit var btnAddUser : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_user_fragment, null)

        initViews(view)
        initListeners()

        return view
    }

    private fun initViews(view : View) {
        edtName = view.findViewById(R.id.edtName)
        edtJob = view.findViewById(R.id.edtJob)
        btnAddUser = view.findViewById(R.id.btnAddUser)
    }

    private fun initListeners() {
        btnAddUser.setOnClickListener {

            val userPostModel = UserPostModel(
                edtName.text.toString(),
                edtJob.text.toString()
            )
            val inputJson = JSONObject( Gson().toJson(userPostModel))

            val userPostRequest =
                JsonObjectRequest(
                    Method.POST,
                    "https://reqres.in/api/users",
                    inputJson,
                    Response.Listener<JSONObject> {
                        Toast.makeText(requireContext(), "User added", Toast.LENGTH_SHORT).show()
                        removeCurrentFragment()
                        Log.e("tag", "*****************=> ${it.toString()}")
                    },
                    Response.ErrorListener {

                    }
                )

            VolleyRequestQueue.getRequestQueue(requireContext()).add(userPostRequest)
        }
    }

    private fun removeCurrentFragment() {
        parentFragmentManager.popBackStack()
    }
}