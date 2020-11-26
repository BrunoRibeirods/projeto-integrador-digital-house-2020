package com.example.filmly.ui.lostpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmly.R
import kotlinx.android.synthetic.main.activity_lost_password.*

class LostPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_password)
        request.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}