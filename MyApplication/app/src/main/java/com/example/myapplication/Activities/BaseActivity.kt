package com.example.myapplication.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

open class BaseActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var TAG = "Codex01"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        window.statusBarColor = resources.getColor(R.color.white)
    }
}