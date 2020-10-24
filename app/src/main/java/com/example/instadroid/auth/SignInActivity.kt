package com.example.instadroid.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.instadroid.MainActivity
import com.example.instadroid.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : AppCompatActivity() {
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        signUp_link_btn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        login_btn.setOnClickListener {
            loginUser()
        }

    }

    private fun loginUser() {
        val email = email_login.text.toString()
        val password = pass_login.text.toString()
        when {
            TextUtils.isEmpty(email) -> email_login.error = "This field is required"
            TextUtils.isEmpty(password) -> pass_login.error = "This field is required"
            else -> {
                pBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            val message = it.exception.toString()
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                            pgBar.visibility = View.GONE
                            auth.signOut()
                        }
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}