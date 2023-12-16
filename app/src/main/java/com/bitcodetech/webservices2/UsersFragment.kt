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

class UsersFragment : Fragment() {

    private lateinit var usersAdapter: UsersAdapter
    private lateinit var recyclerUsers : RecyclerView
    private var users = ArrayList<User>()
    private lateinit var progressDialog : ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.users_fragment, null)

        showProgressDialog()

        initViews(view)
        Worker( UsersHandler() ).execute()

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
    }

    private inner class UsersHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if(msg.obj == null) {
                Toast.makeText(requireActivity(), "Unable to fetch users!", Toast.LENGTH_SHORT).show()
                return
            }

            val apiResponse = msg.obj as ApiResponse
            users.addAll( apiResponse.users!! )
            usersAdapter.notifyDataSetChanged()

            Log.e("tag", "** ${apiResponse.page} ${apiResponse.per_page}**")

            progressDialog.dismiss()
        }
    }

}