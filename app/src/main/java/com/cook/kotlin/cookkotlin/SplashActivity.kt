package com.cook.kotlin.cookkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        turnTo()
    }


    private fun turnTo() {
        handler.postDelayed({
            MainActivity.startActivity(this)
            finish()
        }, 1500)
    }
}
