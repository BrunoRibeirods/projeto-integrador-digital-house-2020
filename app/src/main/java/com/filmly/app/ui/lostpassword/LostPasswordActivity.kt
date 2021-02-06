package com.filmly.app.ui.lostpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.filmly.app.R
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
        Log.d("Lostpass", "createAccount:$email")
        if (!validateForm()) {
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Email de confirmação enviado.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Não foi possivel completar a ação.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = findViewById<EditText>(R.id.email_reset)



        if (TextUtils.isEmpty(email.text.toString()) || !email.text.toString().contains("@")) {
            valid = false
            email.error = "Email inválido."
        }


        return valid
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}