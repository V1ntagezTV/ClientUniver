package com.example.testings.ui.unstudents.ProfileDetails


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testings.R
import com.r0adkll.slidr.Slidr

class ProfileInfoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_unstudent_profile_info)
        setPageInfo()
        val toolbar = supportActionBar
        toolbar?.title = "Направление"
        toolbar?.setDisplayHomeAsUpEnabled(true)
        Slidr.attach(this)
    }

    private fun setPageInfo() {
        findViewById<TextView>(R.id.profile_title).text = ProfileInfoViewModel.profile?.Name
        findViewById<TextView>(R.id.profile_code).text = ProfileInfoViewModel.profile?.Code
        findViewById<TextView>(R.id.profile_fac).text =  ProfileInfoViewModel.profile?.Faculty
        findViewById<TextView>(R.id.profile_all).text =  ProfileInfoViewModel.profile?.All
        findViewById<TextView>(R.id.profile_scores).text =  ProfileInfoViewModel.profile?.Scores
        findViewById<TextView>(R.id.profile_lessons).text = ProfileInfoViewModel.profile?.Lessons

        findViewById<TextView>(R.id.profile_general_och).text = ProfileInfoViewModel.profile?.GeneralTerms?.Intramural
        findViewById<TextView>(R.id.profile_general_och_zao).text = ProfileInfoViewModel.profile?.GeneralTerms?.Intra_Absentia
        findViewById<TextView>(R.id.profile_general_zaoch).text = ProfileInfoViewModel.profile?.GeneralTerms?.Absentia

        findViewById<TextView>(R.id.profile_special_och).text = ProfileInfoViewModel.profile?.SpecialQute?.Intramural
        findViewById<TextView>(R.id.profile_special_och_zao).text = ProfileInfoViewModel.profile?.SpecialQute?.Intra_Absentia
        findViewById<TextView>(R.id.profile_special_zao).text = ProfileInfoViewModel.profile?.SpecialQute?.Absentia

        findViewById<TextView>(R.id.profile_commerc_och).text = ProfileInfoViewModel.profile?.Commercial?.Intramural
        findViewById<TextView>(R.id.profile_commerc_och_zao).text = ProfileInfoViewModel.profile?.Commercial?.Intra_Absentia
        findViewById<TextView>(R.id.profile_commerc_zao).text = ProfileInfoViewModel.profile?.Commercial?.Absentia
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}