package com.example.testings.ui.unstudents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.IOException

class SlideshowFragment : Fragment() {

    val link = "http://sibsu.ru/abitur/bachelor/"
    val list: ArrayList<EducProfileModel> = ArrayList()
    lateinit var recycler: RecyclerView
    lateinit var adapter: EducAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_unstudents, container, false)

        recycler = root.findViewById(R.id.unstud_recyclerView)
        recycler.setItemViewCacheSize(20) //временное решение cache uses for 20 newsview
        adapter = EducAdapter()
        recycler.adapter = adapter
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(context)
        //end init
        setData()

        return root
    }

    private fun setData(){
        view?.findViewById<Button>(R.id.unstud_retry_connection)?.visibility = View.INVISIBLE
        view?.findViewById<ProgressBar>(R.id.unstud_progressBar)?.visibility = View.VISIBLE
        GlobalScope.launch {
            try {

                val doc = Jsoup.connect(link).get()
                val tablePriemKolMest = doc.select("table[itemprop=priemKolMest]").select("tbody").select("tr")

                for (ind in 0 until tablePriemKolMest.size){
                    val code    = tablePriemKolMest.eq(ind).select("td[data-label=Код]").text()
                    val profile = tablePriemKolMest.eq(ind).select("td[data-label=Направление]").text()
                    val faculty = tablePriemKolMest.eq(ind).select("td[data-label=Факультет]").text()
                    val count   = tablePriemKolMest.eq(ind).select("td[data-label=Всего]").text()
                    list.add(EducProfileModel(code, profile, faculty, count))
                }
                GlobalScope.launch(Dispatchers.Main) {
                    view?.findViewById<ProgressBar>(R.id.unstud_progressBar)?.visibility = View.INVISIBLE
                    adapter.Set(list)
                }
            } catch (e: IOException){

            }
        }
    }
}