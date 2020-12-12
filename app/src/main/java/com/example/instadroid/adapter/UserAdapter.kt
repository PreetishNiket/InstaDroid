package com.example.instadroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instadroid.R
import com.example.instadroid.modelClass.User
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(
    private var context: Context,
    private var userList: List<User>,
    private var isFragment: Boolean = false
) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(holder) {
            userNameTV.text = userList[position].userName
            userFullNameTv.text = userList[position].fullName
            Picasso.get().load(userList[position].image).placeholder(R.drawable.profile)
                .into(userProfileImage)
        }
    }

    override fun getItemCount() = userList.size
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userNameTV: TextView = itemView.findViewById(R.id.user_name_search)
    val userFullNameTv: TextView = itemView.findViewById(R.id.user_full_name_search)
    val userProfileImage: CircleImageView = itemView.findViewById(R.id.user_profile_search)
    val followButton: Button = itemView.findViewById(R.id.follow_btn_search)
}