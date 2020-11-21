package com.digitalhouse.projetointegrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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
    }
}