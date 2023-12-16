package com.bitcodetech.webservices2

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject

class UsersFragment : Fragment() {

    private lateinit var usersAdapter: UsersAdapter
    private lateinit var recyclerUsers : RecyclerView
    private var users = ArrayList<User>()
    private lateinit var progressDialog : ProgressDialog
    private lateinit var btnAddUserFragment : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.users_fragment, null)

        showProgressDialog()

        initViews(view)

        //make volley request
        /*val userApiRequest = StringRequest(
            Request.Method.GET,
            "https://reqres.in/api/users?page=1",
            object : Response.Listener<String> {
                override fun onResponse(response: String?) {
                    Log.e("tag", response!!)
                    val apiResponse = Gson().fromJson<ApiResponse>(response, ApiResponse::class.java)
                    users.addAll(apiResponse!!.users!!)
                    usersAdapter.notifyDataSetChanged()
                    progressDialog.dismiss()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.e("tag", "${error?.message}")
                }
            }
        )*/

        val userApiRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://reqres.in/api/users?page=1",
            null,
            object : Response.Listener<JSONObject> {
                override fun onResponse(response: JSONObject?) {
                    Log.e("tag", response!!.toString())
                    val apiResponse = Gson().fromJson<ApiResponse>(response.toString(), ApiResponse::class.java)
                    users.addAll(apiResponse!!.users!!)
                    usersAdapter.notifyDataSetChanged()
                    progressDialog.dismiss()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.e("tag", "${error?.message}")
                }
            }
        )

        /*val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(userApiRequest)*/

        VolleyRequestQueue.getRequestQueue(requireContext()).add(userApiRequest)

        return view
    }

    private fun showProgressDialog() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setMessage("Fetching users....")
        progressDialog.show()
    }

    private fun initViews(view: View) {
        recyclerUsers = view.findViewById(R.id.recyclerUsers)
        recyclerUsers.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        usersAdapter = UsersAdapter(users)
        recyclerUsers.adapter = usersAdapter

        btnAddUserFragment = view.findViewById(R.id.btnAddUserFragment)

        btnAddUserFragment.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.mainContainer, AddUserFragment(), null)
                .addToBackStack(null)
                .commit()
        }
    }



}