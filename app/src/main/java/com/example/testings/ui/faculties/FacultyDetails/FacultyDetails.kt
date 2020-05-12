package com.example.testings.ui.faculties.FacultyDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.testings.R

class FacultyDetails: Fragment() {

    lateinit var linLayout: LinearLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_faculty_details, container, false)
        linLayout = root.findViewById(R.id.facDet_LinearLayout)
        addProfiles(root)
        return root
    }

    private fun addProfiles(view: View){
        view.findViewById<TextView>(R.id.facDet_title).text = FacultyDetailsModel.currentModel?.Name
        view.findViewById<TextView>(R.id.facDet_content).text = FacultyDetailsModel.currentModel?.Content
        val profiles = FacultyDetailsModel.currentModel!!.Profiles
        for (ind in 0 until profiles.size){
            val textv = TextView(view.context)
            textv.text = profiles[ind]
            textv.setPadding(10,15,10,0)
            linLayout.addView(textv)
        }
    }

}
