package com.example.filmly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)

    }
}