package com.example.testings.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

//TODO: разобраться с перезагрузкой изображений при перелистывании RecyclerView

class NewsFragment : Fragment()
{
    private var list = ArrayList<NewsModel>()
    private lateinit var adapter: NewsAdapter
    private lateinit var news_recyclerView: RecyclerView
    private var pageNumber: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_news, container, false)
        //start init контейнера новостей
        news_recyclerView = root.findViewById(R.id.news_recyclerView)
        news_recyclerView.setItemViewCacheSize(20) //временное решение cache uses for 20 newsview
        adapter = NewsAdapter()
        news_recyclerView.adapter = adapter
        news_recyclerView.itemAnimator = DefaultItemAnimator()
        news_recyclerView.layoutManager = LinearLayoutManager(context)
        //end init
        setRecyclerViewScrollListener()
        getData(pageNumber++)
        return root
    }

    fun getData(pageNum: Int) {
        GlobalScope.launch {
            try {
                val doc: Document = Jsoup.connect("http://sibsu.ru/category/novosti/page/${pageNum}").get()
                val element = doc.select("div[class=item-inner clearfix]")

                for (i in 0 until element.size) {
                    val urlImage = element
                        .select("div[class=featured clearfix]")
                        .eq(i)
                        .select("a[class=img-holder]")
                        .attr("data-src")
                        .replace("-210x136", "")

                    val title = element
                        .select("h2[class=title]")
                        .select("a")
                        .eq(i)
                        .text()

                    val postTime = element
                        .select("div[class=post-meta]")
                        .select("span[class=time]")
                        .eq(i)
                        .text()

                    val description = element
                        .select("div[class=post-summary]")
                        .eq(i)
                        .text()

                    val urldetails = element
                        .select("h2[class=title]")
                        .select("a")
                        .eq(i)
                        .attr("href")

                    list.add(NewsModel(title, postTime, urlImage, description, urldetails))
                }
                GlobalScope.launch(Dispatchers.Main) {
                    view?.findViewById<ProgressBar>(R.id.news_progressBar)?.visibility = View.INVISIBLE
                    view?.findViewById<Button>(R.id.news_retry_connection)?.visibility = View.INVISIBLE
                    adapter.set(list)
                }
            }
            catch (e: IOException) {
                GlobalScope.launch(Dispatchers.Main) {
                    val retry = view?.findViewById<Button>(R.id.news_retry_connection)
                    retry?.setOnClickListener{
                        pageNumber = 1
                        view?.findViewById<ProgressBar>(R.id.news_progressBar)?.visibility = View.VISIBLE
                        view?.findViewById<Button>(R.id.news_retry_connection)?.visibility = View.INVISIBLE
                        OnClickRetryConn(pageNumber++)
                    }
                    view?.findViewById<ProgressBar>(R.id.news_progressBar)?.visibility = View.INVISIBLE
                    retry?.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun OnClickRetryConn(pageNum: Int){
        getData(pageNum)
    }

    fun setRecyclerViewScrollListener() {
        news_recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linLayoutMan = news_recyclerView.layoutManager as LinearLayoutManager?
                if (linLayoutMan?.findLastCompletelyVisibleItemPosition() == list.size - 1){
                    getData(++pageNumber)
                }
            }
        })
    }
}