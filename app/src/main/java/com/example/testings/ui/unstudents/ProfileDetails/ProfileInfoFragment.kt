package com.example.testings.ui.unstudents.ProfileDetails


import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.testings.R
import com.example.testings.ui.unstudents.EducProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.IOException

class ProfileInfoFragment: Fragment() {

    var link: String? = null
    var profile: EducProfileModel? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_unstudent_profile_info, container, false)

        link = arguments?.getString("link")
        GlobalScope.launch {
            setData(link, root)
        }

        return root
    }

    private fun setData(link: String?, view: View) {
        try {
            val doc = Jsoup.connect(link).get()
            val htmlcontent = doc.select("div[class=col-md-8 bs-shortcode-col]")
            val code = htmlcontent.select("div[class=row bs-shortcode-row bs-shortcode-row-2-column]").eq(0).select("div[class=col-xs-9 bs-shortcode-col]").text()
            val fac = htmlcontent.select("div[class=row bs-shortcode-row bs-shortcode-row-2-column]").eq(1).select("div[class=col-xs-9 bs-shortcode-col]").text()
            val name = doc.select("div[class=post-header post-tp-1-header]").text()
            GlobalScope.launch(Dispatchers.Main) {
                profile = EducProfileModel(code, name, fac)
                setPageInfo(view)
            }
        } catch (ex: IOException) {

        }
    }

    private fun setPageInfo(view: View) {
        view.findViewById<TextView>(R.id.profile_title).text = profile?.Name
        view.findViewById<TextView>(R.id.profile_code).text = profile?.Code
        view.findViewById<TextView>(R.id.profile_fac).text =  profile?.Faculty

/*      view.findViewById<TextView>(R.id.profile_general_och).text = profile?.GeneralTerms?.Intramural
        view.findViewById<TextView>(R.id.profile_general_och_zao).text = profile?.GeneralTerms?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_general_zaoch).text = profile?.GeneralTerms?.Absentia

        view.findViewById<TextView>(R.id.profile_special_och).text = profile?.SpecialQute?.Intramural
        view.findViewById<TextView>(R.id.profile_special_och_zao).text = profile?.SpecialQute?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_special_zao).text = profile?.SpecialQute?.Absentia

        view.findViewById<TextView>(R.id.profile_commerc_och).text = profile?.Commercial?.Intramural
        view.findViewById<TextView>(R.id.profile_commerc_och_zao).text = profile?.Commercial?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_commerc_zao).text = profile?.Commercial?.Absentia*/
    }
}