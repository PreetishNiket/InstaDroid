package com.example.instadroid.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instadroid.MainActivity
import com.example.instadroid.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signIn_link_btn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
        signUp_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}