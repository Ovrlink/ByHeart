package com.example.byheart.presentation.load

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.byheart.R
import com.example.byheart.presentation.MainActivity

class Load_screen : AppCompatActivity() {
    lateinit var btnEnter: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_screen)
        btnEnter= findViewById(R.id.enter)
        btnEnter.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
        }

    }
}