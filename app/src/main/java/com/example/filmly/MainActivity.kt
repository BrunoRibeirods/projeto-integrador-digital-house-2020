package com.example.filmly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = ViewMoreFragment.newInstance()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fl_main_test, fragment)
            addToBackStack(null)
            commit()
        }

        val fragment2 = FavoriteListsFragment.newInstance()

        Handler().postDelayed({
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_main_test, fragment2)
                addToBackStack(null)
                commit()
            }
        }, 5000)
    }
}