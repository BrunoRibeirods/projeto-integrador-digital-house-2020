package com.example.filmly.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.filmly.R
import com.example.filmly.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    lateinit var animation_logo: Animation
    lateinit var animation_dot: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        animation_logo = AnimationUtils.loadAnimation(this, R.anim.logo_splash_anim)
        filmly_logo_splashScreen.animation = animation_logo

        animation_dot = AnimationUtils.loadAnimation(this, R.anim.logo_splash_anim)
        lazy_dot_splashScreen.animation = animation_dot


        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)

    }
}