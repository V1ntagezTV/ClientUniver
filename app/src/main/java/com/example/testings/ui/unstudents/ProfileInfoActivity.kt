package com.example.testings.ui.unstudents


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testings.R
import com.r0adkll.slidr.Slidr

class ProfileInfoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_unstudent_profile_info)
        val toolbar = supportActionBar
        toolbar?.title = "Направление"
        toolbar?.setDisplayHomeAsUpEnabled(true)
        Slidr.attach(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}