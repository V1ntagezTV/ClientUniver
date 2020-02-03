package com.example.testings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firstActivity = Intent(this, MainActivity::class.java)
        startActivity(firstActivity)
        finish()
    }
}