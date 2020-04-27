package com.example.testings.ui.faculties.FacultyDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.example.testings.R
import com.r0adkll.slidr.Slidr
import org.w3c.dom.Text

class FacultyDetails: AppCompatActivity() {

    lateinit var linLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Slidr.attach(this)
        setContentView(R.layout.fragment_faculty_details)
        linLayout = findViewById(R.id.facDet_LinearLayout)
        addProfiles(FacultyDetailsModel.currentData)

    }

    private fun addProfiles(data: ArrayList<String>){
        findViewById<TextView>(R.id.facDet_title).text = FacultyDetailsModel.currentModel?.Name
        findViewById<TextView>(R.id.facDet_content).text = FacultyDetailsModel.currentModel?.Content
        for (ind in 0 until data.size){
            val textv = TextView(this)
            textv.text = data[ind]
            linLayout.addView(textv)
        }
    }

}
