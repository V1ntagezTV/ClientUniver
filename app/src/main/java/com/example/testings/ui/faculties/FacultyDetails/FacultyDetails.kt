package com.example.testings.ui.faculties.FacultyDetails

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testings.R
import com.example.testings.ui.faculties.FacultyModel
import com.r0adkll.slidr.Slidr

class FacultyDetails: AppCompatActivity() {

    lateinit var linLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Slidr.attach(this)
        setContentView(R.layout.fragment_faculty_details)
        linLayout = findViewById(R.id.facDet_LinearLayout)
        addProfiles()
    }

    private fun addProfiles(){
        findViewById<TextView>(R.id.facDet_title).text = FacultyDetailsModel.currentModel?.Name
        findViewById<TextView>(R.id.facDet_content).text = FacultyDetailsModel.currentModel?.Content
        val profiles = FacultyDetailsModel.currentModel!!.Profiles
        for (ind in 0 until profiles.size){
            val textv = TextView(this)
            textv.text = profiles[ind]
            textv.setPadding(0,10,0,0)
            linLayout.addView(textv)
        }
    }

}
