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
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var userList: MutableList<User>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userList = ArrayList()
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        view.rv_search.layoutManager = LinearLayoutManager(context)
        view.rv_search.adapter = context?.let { UserAdapter(it, userList as ArrayList<User>, true) }
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

    }
}