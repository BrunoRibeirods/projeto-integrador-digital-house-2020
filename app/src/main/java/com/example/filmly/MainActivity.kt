package com.example.filmly

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_home -> {
                    findNavController(R.id.navHostFragment_MainActivity).navigate(R.id.homeFragment)
                    true
                }
                R.id.page_search -> {
                    findNavController(R.id.navHostFragment_MainActivity).navigate(R.id.searchFragment)
                    true
                }
                R.id.page_lists -> {
                    findNavController(R.id.navHostFragment_MainActivity).navigate(R.id.yourListsFragment)
                    true
                }
                R.id.page_profile -> {
                    findNavController(R.id.navHostFragment_MainActivity).navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }
}