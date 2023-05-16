package com.example.byheart.presentation

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.byheart.R
import com.example.byheart.databinding.ActivityMainBinding
import com.example.byheart.presentation.pager.PagerFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.container, PagerFragment()).commit() //add navigation bar

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0)
                    supportFragmentManager.popBackStackImmediate()
                else
                    finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

}