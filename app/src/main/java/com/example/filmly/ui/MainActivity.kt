package com.example.filmly.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.filmly.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavigationController()
    }

    fun setNavigationController() {
        val navController = findNavController(R.id.navHostFragment_MainActivity)
        bottom_navigation.setupWithNavController(navController)

    }
}