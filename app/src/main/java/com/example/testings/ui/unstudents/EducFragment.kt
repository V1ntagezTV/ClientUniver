package com.example.testings.ui.unstudents

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.IOException

class EducFragment : Fragment() {

    private val link = "http://sibsu.ru/abitur/bachelor/"
    val list: ArrayList<EducProfileModel> = ArrayList()
    lateinit var retry: Button
    lateinit var progressBar: ProgressBar
    lateinit var recycler: RecyclerView
    lateinit var adapter: EducAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = EducAdapter(findNavController())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_unstudents, container, false)
        retry = root.findViewById(R.id.unstud_retry_connection)
        progressBar = root.findViewById(R.id.unstud_progressBar)
        initRecyclerView(root)
        initAllListeners()
        if (adapter.itemCount == 0) {
            retry.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            setData()
        }
        return root
    }

    private fun initRecyclerView(view: View){
        recycler = view.findViewById(R.id.unstud_recyclerView)
        recycler.adapter = adapter
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(context)
    }

    private fun initAllListeners(){
        retry.setOnClickListener {
                adapter.CleanList()
                list.clear()
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.VISIBLE
                retry.visibility = View.INVISIBLE
                setData()
        }
    }

    private fun setData(){

        GlobalScope.async {
            try {
                val doc = Jsoup.connect(link).get()
                val tablePriemKolMest = doc.select("table[itemprop=priemKolMest]").select("tbody").select("tr")
                val tableLessonsScores = doc.select("table[itemprop=priemExam]").select("tbody").select("tr")

                for (ind in 0 until tablePriemKolMest.size){
                    val code    = tablePriemKolMest.eq(ind).select("td[data-label=Код]").text()
                    val profile = tablePriemKolMest.eq(ind).select("td[data-label=Направление]").text()
                    val faculty = tablePriemKolMest.eq(ind).select("td[data-label=Факультет]").text()
                    val count   = tablePriemKolMest.eq(ind).select("td[data-label=Всего]").text()

                    val SpecialQ_och   = tablePriemKolMest.eq(ind).select("td[data-label=Особ. квота очн.]").first().text()
                    val SpecialQ_zaoch = tablePriemKolMest.eq(ind).select("td[data-label=Особ. квота очн.]").last().text()
                    val SpecialQ_och_zao = tablePriemKolMest.eq(ind).select("td[data-label=Особ. квота очн.-заочн.]").text()

                    val GeneralT_och = tablePriemKolMest.eq(ind).select("td[data-label=Общие усл. очн.]").text()
                    val GeneralT_zaoch = tablePriemKolMest.eq(ind).select("td[data-label=Общие усл. заочн.]").text()
                    val GeneralT_och_zao = tablePriemKolMest.eq(ind).select("td[data-label=Общие усл. очн.-заочн.]").text()

                    val Commercial_och = tablePriemKolMest.eq(ind).select("td[data-label=Договорн. очн.]").text()
                    val Commercial_zaoch = tablePriemKolMest.eq(ind).select("td[data-label=Договорн. заочн.]").text()
                    val Commercial_och_zao = tablePriemKolMest.eq(ind).select("td[data-label=Договорн. очн.-заочн.]").text()

                    val prof_model = EducProfileModel(code, profile, faculty, count)
                    prof_model.SpecialQute = EducType(SpecialQ_och, SpecialQ_och_zao, SpecialQ_zaoch)
                    prof_model.GeneralTerms = EducType(GeneralT_och, GeneralT_och_zao, GeneralT_zaoch)
                    prof_model.Commercial = EducType(Commercial_och, Commercial_och_zao, Commercial_zaoch)
                    list.add(prof_model)
                }
                for (ind in 0 until tableLessonsScores.size){
                    val name = tableLessonsScores.eq(ind).select("td[data-label=Направление]").text()
                    val lessons = tableLessonsScores.eq(ind)
                        .select("td[data-label=Экзамены]")
                        .select("ol").select("li")
                    val scores = tableLessonsScores.eq(ind).select("td[data-label=Мин.баллы]").text()
                    val data = list.find { it.Name == name }
                    data?.Lessons = ArrayList(lessons.size)
                    for (lessInd in 0 until lessons.size){
                        data?.Lessons?.add(lessons[lessInd].text())
                    }
                    data?.Scores = scores
                }
                GlobalScope.launch(Dispatchers.Main) {
                    progressBar.visibility = View.INVISIBLE
                    retry.visibility = View.INVISIBLE
                    adapter.Set(list)
                }
            } catch (e: IOException){
                GlobalScope.launch(Dispatchers.Main) {
                    retry.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}