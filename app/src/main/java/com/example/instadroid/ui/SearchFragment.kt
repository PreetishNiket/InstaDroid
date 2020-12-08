package com.example.instadroid.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instadroid.R
import com.example.instadroid.adapter.UserAdapter
import com.example.instadroid.modelClass.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var userList: MutableList<User>? = null
    private var userAdapter:UserAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userList = ArrayList()
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        view.rv_search.layoutManager = LinearLayoutManager(context)
        userAdapter = context?.let { UserAdapter(it, userList as ArrayList<User>, true) }
        view.rv_search.adapter=userAdapter
        view.search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (view.search_edit_text.text.toString() == "") {
                    Toast.makeText(view.context, "Empty", Toast.LENGTH_SHORT).show()
                } else {
                    view.rv_search.visibility = View.VISIBLE
                    retrieveUsers()
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        return view
    }

    private fun retrieveUsers() {
        val userRef=FirebaseDatabase.getInstance().reference.child("Users")
        userRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (view!!.search_edit_text.text.toString() == "") {
                    userList?.clear()
                    for (snap in snapshot.children){
                        val user=snap.getValue(User::class.java)
                        userList?.add(user!!)
                    }
                    userAdapter?.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}