package com.example.testings.ui.unstudents.ProfileDetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.testings.R
import com.example.testings.ui.unstudents.EducProfileModel
import java.io.IOException

class ProfileInfoFragment: Fragment() {

    var profile: EducProfileModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_unstudent_profile_info, container, false)
        return root
    }

    private suspend fun setData(link: String) {
        try {

        } catch (ex: IOException) {

        }
    }

    private fun setPageInfo(view: View) {
        val lessons = profile?.Lessons?.get(0) + "\n" + profile?.Lessons?.get(1) + "\n" + profile?.Lessons?.get(2)
        view.findViewById<TextView>(R.id.profile_title).text = profile?.Name
        view.findViewById<TextView>(R.id.profile_code).text = profile?.Code
        view.findViewById<TextView>(R.id.profile_fac).text =  profile?.Faculty
        view.findViewById<TextView>(R.id.profile_scores).text =  profile?.Scores?.split(' ')?.joinToString("\n")
        view.findViewById<TextView>(R.id.profile_lessons).text = lessons

        view.findViewById<TextView>(R.id.profile_general_och).text = profile?.GeneralTerms?.Intramural
        view.findViewById<TextView>(R.id.profile_general_och_zao).text = profile?.GeneralTerms?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_general_zaoch).text = profile?.GeneralTerms?.Absentia

        view.findViewById<TextView>(R.id.profile_special_och).text = profile?.SpecialQute?.Intramural
        view.findViewById<TextView>(R.id.profile_special_och_zao).text = profile?.SpecialQute?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_special_zao).text = profile?.SpecialQute?.Absentia

        view.findViewById<TextView>(R.id.profile_commerc_och).text = profile?.Commercial?.Intramural
        view.findViewById<TextView>(R.id.profile_commerc_och_zao).text = profile?.Commercial?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_commerc_zao).text = profile?.Commercial?.Absentia
    }
}