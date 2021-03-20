package com.tejsumeru.bankingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var TIME_LIMIT = 2000

        Handler().postDelayed({
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_LIMIT.toLong())
    }
}