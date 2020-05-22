package com.example.testings.ui.unstudents.ProfileDetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.testings.R
import com.example.testings.ui.unstudents.EducProfileModel
import com.example.testings.ui.unstudents.EducType
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

            val lessTable = htmlcontent
                .select("div[class=table-responsive]")[1]
                .select("table[class=table]")
                .select("tbody").select("tr")[0]
                .select("td[data-label=Миним. баллы]").html().split("<br>")

            val placesTable = htmlcontent
                .select("div[class=table-responsive]")[0]
                .select("table[class=table]")
                .select("tbody").select("tr")

            val genOch = placesTable[0].select("td[data-label=бюджетных мест]").text()
            val genZao = placesTable[1].select("td[data-label=бюджетных мест]").text()
            val genOchZao = placesTable[2].select("td[data-label=бюджетных мест]").text()

            val specOch = placesTable[0].select("td[data-label=мест по особой квоте]").text()
            val specZao = placesTable[1].select("td[data-label=мест по особой квоте]").text()
            val specOchZao = placesTable[2].select("td[data-label=мест по особой квоте]").text()

            val commOch = placesTable[0].select("td[data-label=мест по договорам]").text()
            val commZao = placesTable[1].select("td[data-label=мест по договорам]").text()
            val commOchZao = placesTable[2].select("td[data-label=мест по договорам]").text()
            GlobalScope.launch(Dispatchers.Main) {
                profile = EducProfileModel(code, name, fac)
                profile?.Lessons = lessTable
                profile?.GeneralTerms = EducType(genOch, genZao, genOchZao)
                profile?.Commercial = EducType(commOch, commZao, commOchZao)
                profile?.SpecialQute = EducType(specOch, specZao, specOchZao)
                setPageInfo(view)
            }
        } catch (ex: IOException) {
            GlobalScope.launch(Dispatchers.Main) {
                val toast = Toast.makeText(context, "Ошибка интернет соединения", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    private fun setPageInfo(view: View) {
        val lessonsViewList: ArrayList<TextView> = arrayListOf(
            view.findViewById(R.id.unstud_prof_first),
            view.findViewById(R.id.unstud_prof_second),
            view.findViewById(R.id.unstud_prof_third)
        )
        val scoreViewList: ArrayList<TextView> = arrayListOf(
            view.findViewById(R.id.unstud_prof_first_count),
            view.findViewById(R.id.unstud_prof_second_count),
            view.findViewById(R.id.unstud_prof_third_count)
        )
        for (ind in 0 until profile?.Lessons!!.size) {
            if (profile?.Lessons!![ind].trim().isNotEmpty()) {
                val less = profile?.Lessons!![ind].trim().split('-')
                lessonsViewList[ind].text = less[0].trim()
                scoreViewList[ind].text = less[1].trim()
            }
        }

        view.findViewById<TextView>(R.id.unstud_prof_code).text = profile?.Code
        view.findViewById<TextView>(R.id.unstud_prof_faculty).text =  profile?.Faculty
        view.findViewById<TextView>(R.id.unstud_prof_all).text =
            (profile?.Commercial!!.getAll()
            + profile?.GeneralTerms!!.getAll()
            + profile?.SpecialQute!!.getAll()).toString()

        view.findViewById<TextView>(R.id.profile_general_och).text = profile?.GeneralTerms?.Intramural
        view.findViewById<TextView>(R.id.profile_general_och_zao).text = profile?.GeneralTerms?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_general_zao).text = profile?.GeneralTerms?.Absentia

        view.findViewById<TextView>(R.id.profile_special_och).text = profile?.SpecialQute?.Intramural
        view.findViewById<TextView>(R.id.profile_special_och_zao).text = profile?.SpecialQute?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_special_zao).text = profile?.SpecialQute?.Absentia

        view.findViewById<TextView>(R.id.profile_commerc_och).text = profile?.Commercial?.Intramural
        view.findViewById<TextView>(R.id.profile_commerc_och_zao).text = profile?.Commercial?.Intra_Absentia
        view.findViewById<TextView>(R.id.profile_commerc_zao).text = profile?.Commercial?.Absentia
    }
}