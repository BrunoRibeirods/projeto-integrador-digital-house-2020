package com.example.filmly.ui.lostpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.filmly.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_lost_password.*

class LostPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_password)
        auth = FirebaseAuth.getInstance()

        request.setOnClickListener {

                resetPassword(email_reset.text.toString())

        }
    }

    private fun resetPassword(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "for", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "nao foi", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}