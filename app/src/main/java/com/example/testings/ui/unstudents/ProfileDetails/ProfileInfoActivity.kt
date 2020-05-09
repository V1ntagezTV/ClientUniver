package com.example.testings.ui.unstudents.ProfileDetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.testings.R

class ProfileInfoActivity: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_unstudent_profile_info, container, false)
        setPageInfo(root)
        return root
    }

    private fun setPageInfo(view: View) {
        val lessons = ProfileInfoViewModel.profile?.Lessons?.get(0) + "\n" + ProfileInfoViewModel.profile?.Lessons?.get(1) + "\n" + ProfileInfoViewModel.profile?.Lessons?.get(2)
        view.findViewById<TextView>(R.id.profile_title).text = ProfileInfoViewModel.profile?.Name
        view.findViewById<TextView>(R.id.profile_code).text = ProfileInfoViewModel.profile?.Code
        view.findViewById<TextView>(R.id.profile_fac).text =  ProfileInfoViewModel.profile?.Faculty
        view.findViewById<TextView>(R.id.profile_all).text =  ProfileInfoViewModel.profile?.All
        view.findViewById<TextView>(R.id.profile_scores).text =  ProfileInfoViewModel.profile?.Scores?.split(' ')?.joinToString("\n")
        view.findViewById<TextView>(R.id.profile_lessons).text = lessons

        view.findViewById<TextView>(R.id.profile_general_och).text = ProfileInfoViewModel.profile?.GeneralTerms?.Intramural
        view.findViewById<TextView>(R.id.profile_general_och_zao).text = ProfileInfoViewModel.profile?.GeneralTerms?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_general_zaoch).text = ProfileInfoViewModel.profile?.GeneralTerms?.Absentia

        view.findViewById<TextView>(R.id.profile_special_och).text = ProfileInfoViewModel.profile?.SpecialQute?.Intramural
        view.findViewById<TextView>(R.id.profile_special_och_zao).text = ProfileInfoViewModel.profile?.SpecialQute?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_special_zao).text = ProfileInfoViewModel.profile?.SpecialQute?.Absentia

        view.findViewById<TextView>(R.id.profile_commerc_och).text = ProfileInfoViewModel.profile?.Commercial?.Intramural
        view.findViewById<TextView>(R.id.profile_commerc_och_zao).text = ProfileInfoViewModel.profile?.Commercial?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_commerc_zao).text = ProfileInfoViewModel.profile?.Commercial?.Absentia
    }
}