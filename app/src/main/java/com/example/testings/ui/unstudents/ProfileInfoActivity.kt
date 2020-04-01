package com.example.testings.ui.unstudents


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testings.R
import com.r0adkll.slidr.Slidr

class ProfileInfoActivity: AppCompatActivity() {

    private var Profile: EducProfileModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Profile = getPageInfo()
        setContentView(R.layout.fragment_unstudent_profile_info)
        setPageInfo()
        val toolbar = supportActionBar
        toolbar?.title = "Направление"
        toolbar?.setDisplayHomeAsUpEnabled(true)
        Slidr.attach(this)
    }

    private fun getPageInfo(): EducProfileModel?{
        val intent = getIntent()
        val code = intent.getStringExtra("code")
        val fac = intent.getStringExtra("fac")
        val name = intent.getStringExtra("name")
        val all = intent.getStringExtra("all")
        val result = EducProfileModel(code, name, fac, all)
        return result
    }

    private fun setPageInfo() {
        findViewById<TextView>(R.id.profile_title).text = Profile?.Name
        findViewById<TextView>(R.id.profile_code).text = Profile?.Code
        findViewById<TextView>(R.id.profile_fac).text = Profile?.Faculty
        findViewById<TextView>(R.id.profile_all).text = Profile?.All
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}