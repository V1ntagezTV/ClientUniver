package com.example.testings.ui.news

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private var pageNumber: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_news, container, false)
        initRecyclerView(root)
        refreshLayout = root.findViewById(R.id.news_swipe_refresh)
        refreshLayout.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                adapter.CleanList()
                pageNumber = 0
                list.clear()
                setData(pageNumber++)
            }
        })
        setRecyclerViewScrollListener()
        setData(pageNumber++)
        return root
    }

    private fun initRecyclerView(view: View){
        newsRecyclerView = view.findViewById(R.id.news_recyclerView)
        newsRecyclerView.setItemViewCacheSize(20) //временное решение cache uses for 20 newsview
        adapter = NewsAdapter()
        newsRecyclerView.adapter = adapter
        newsRecyclerView.itemAnimator = DefaultItemAnimator()
        newsRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    fun setData(pageNum: Int) {
        GlobalScope.launch {
            try {
                val doc: Document = Jsoup.connect("http://sibsu.ru/category/novosti/page/${pageNum}").get()
                val element = doc.select("div[class=item-inner clearfix]")

                for (ind in 0 until element.size) {
                    val urlImage = element
                        .select("div[class=featured clearfix]")
                        .eq(ind)
                        .select("a[class=img-holder]")
                        .attr("data-src")
                        .replace("-210x136", "")

                    val title = element
                        .select("h2[class=title]")
                        .select("a")
                        .eq(ind)
                        .text()

                    val postTime = element
                        .select("div[class=post-meta]")
                        .select("span[class=time]")
                        .eq(ind)
                        .text()

                    val description = element
                        .select("div[class=post-summary]")
                        .eq(ind)
                        .text()

                    val urldetails = element
                        .select("h2[class=title]")
                        .select("a")
                        .eq(ind)
                        .attr("href")

                    list.add(NewsModel(title, postTime, urlImage, description, urldetails))
                }
                GlobalScope.launch(Dispatchers.Main) {
                    view?.findViewById<ProgressBar>(R.id.news_progressBar)?.visibility = View.INVISIBLE
                    view?.findViewById<Button>(R.id.news_retry_connection)?.visibility = View.INVISIBLE
                    adapter.Set(list)
                    if (refreshLayout.isRefreshing) {
                        refreshLayout.isRefreshing = false
                    }
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
        setData(pageNum)
    }

    fun setRecyclerViewScrollListener() {
        newsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linLayoutMan = newsRecyclerView.layoutManager as LinearLayoutManager?
                if (linLayoutMan?.findLastCompletelyVisibleItemPosition() == list.size - 1){
                    setData(++pageNumber)
                }
            }
        })
    }
}