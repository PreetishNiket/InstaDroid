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
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*
import kotlin.collections.HashMap

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signIn_link_btn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
        signUp_btn.setOnClickListener {
            createAccount()
        }
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }
    private val db by lazy {
        FirebaseDatabase.getInstance().reference
    }
    lateinit var currentUserId: String

    private fun createAccount() {
        val fullName = fullName_signUp.text.toString()
        val userName = userName_signUp.text.toString()
        val email = email_signUp.text.toString()
        val password = pass_signUp.text.toString()
        when {
            TextUtils.isEmpty(fullName) -> fullName_signUp.error = "This field is required"
            TextUtils.isEmpty(userName) -> userName_signUp.error = "This field is required"
            TextUtils.isEmpty(email) -> email_signUp.error = "This field is required"
            TextUtils.isEmpty(password) -> pass_signUp.error = "This field is required"
            else -> {
                pgBar.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            saveUserInfo(fullName, userName, email)
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


    private fun saveUserInfo(fullName: String, userName: String, email: String) {
        currentUserId = auth.currentUser!!.uid

        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["fullName"] = fullName.toLowerCase(Locale.ROOT)
        userMap["userName"] = userName.toLowerCase(Locale.ROOT)
        userMap["email"] = email
        userMap["bio"] = "Hey I am new to InstaDroid"
        userMap["image"] =
            "https://firebasestorage.googleapis.com/v0/b/instadroid-5c83f.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=d0549690-917e-4854-8cff-d94e9e5e095f"

        db.child("Users").child(currentUserId).setValue(userMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    pgBar.visibility = View.GONE
                    Toast.makeText(this, "Account created Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    val message = it.exception.toString()
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    pgBar.visibility = View.GONE
                    auth.signOut()
                }
            }
    }
}