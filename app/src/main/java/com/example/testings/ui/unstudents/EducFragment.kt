package com.example.testings.ui.unstudents

import android.os.Bundle
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
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.IOException

class EducFragment : Fragment() {

    private val link = "http://sibsu.ru/abiturientu/"
    val list: ArrayList<ProfileItemModel> = ArrayList()
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
            GlobalScope.launch {
                setData()
            }
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
            adapter.cleanList()
            list.clear()
            adapter.notifyDataSetChanged()
            progressBar.visibility = View.VISIBLE
            retry.visibility = View.INVISIBLE
            GlobalScope.launch {
                setData()
            }
        }
    }

    private suspend fun setData(){
        try {
            val doc = Jsoup.connect(link).get()
            val htmlcontent = doc.select("div[class=entry-content clearfix]")
            val fac_titles = htmlcontent.select("h3[style=text-align: center;]")

            val ol_tables = htmlcontent.select("ol")
            for (tableInd in 0 until ol_tables.size){
                val tableContent = ol_tables[tableInd].select("li")
                for (profInd in 0 until tableContent.size) {
                    val title = tableContent.select("li").eq(profInd).text()
                    val link = tableContent
                        .select("li")
                        .eq(profInd)
                        .select("a")
                        .attr("href")
                    val item = ProfileItemModel(title, link)
                    list.add(item)
                }
            }
            GlobalScope.launch(Dispatchers.Main) {
                adapter.setList(list)
                progressBar.visibility = View.INVISIBLE
                retry.visibility = View.INVISIBLE
            }
        } catch (e: IOException){
            GlobalScope.launch(Dispatchers.Main) {
                retry.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }
    }
}