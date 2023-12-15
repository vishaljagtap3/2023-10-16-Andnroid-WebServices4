package com.bitcodetech.webservices2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class UsersAdapter(
    private val users: ArrayList<User>
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    inner class UserViewHolder(view: View) : ViewHolder(view) {
        val imgUser: ImageView
        val txtUserName: TextView
        val txtEmail: TextView

        init {
            imgUser = view.findViewById(R.id.imgUser)
            txtUserName = view.findViewById(R.id.txtUserName)
            txtEmail = view.findViewById(R.id.txtEmail)
        }
    }

    override fun getItemCount() = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_view, null)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.imgUser.setImageBitmap(user.avatarBitmap)
        holder.txtUserName.text = "${user.firstName} ${user.lastName}"
        holder.txtEmail.text = user.email
    }
}