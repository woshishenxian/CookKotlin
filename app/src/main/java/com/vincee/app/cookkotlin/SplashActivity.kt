package com.vincee.app.cookkotlin

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.vincee.app.R

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
