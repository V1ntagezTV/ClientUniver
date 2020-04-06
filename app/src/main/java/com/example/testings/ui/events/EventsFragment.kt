package com.example.testings.ui.events

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
import java.io.IOException

class EventsFragment: Fragment(){

    private lateinit var event_recyclerView: RecyclerView
    private lateinit var adapter: EventsAdapter
    private var list: ArrayList<EventModel> = ArrayList()
    private var pageNumber = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val root = inflater.inflate(R.layout.fragment_events, container, false)

        event_recyclerView = root.findViewById(R.id.event_recyclerView)
        event_recyclerView.setItemViewCacheSize(20)
        adapter = EventsAdapter()
        event_recyclerView.adapter = adapter
        event_recyclerView.itemAnimator = DefaultItemAnimator()
        event_recyclerView.layoutManager = LinearLayoutManager(context)
        setRecyclerViewScrollListener()
        setData(pageNumber++)

        root.findViewById<Button>(R.id.event_retry_connection).setOnClickListener {
            pageNumber = 1
            setData(pageNumber++)
        }

        return root
    }

    fun setData(pageNum: Int) {
        GlobalScope.launch {
            try{
                val doc = Jsoup.connect("http://sibsu.ru/category/objavlenija/page/${pageNum}").get()
                val content = doc.select("div[class=listing listing-blog listing-blog-1 clearfix  columns-1]")
                    .select("article")

                for (item in 0 until content.size){
                    val title = content
                        .select("h2[class=title]")
                        .eq(item)
                        .text()

                    val date = content
                        .select("div[class=post-meta]")
                        .eq(item)
                        .text()

                    val desc = content
                        .select("div[class=post-summary]")
                        .eq(item)
                        .text()

                    val eventPageLink = content
                        .select("h2[class=title]")
                        .select("a[class=post-url post-title]")
                        .eq(item)
                        .attr("href")

                    val eventModel = EventModel()
                    eventModel.Title = title
                    eventModel.PostDate = date
                    eventModel.SmallDescription = desc
                    eventModel.EventPageLink = eventPageLink
                    list.add(eventModel)
                }
                GlobalScope.launch(Dispatchers.Main) {
                    view?.findViewById<ProgressBar>(R.id.event_progressBar)?.visibility = View.INVISIBLE
                    view?.findViewById<Button>(R.id.event_retry_connection)?.visibility = View.INVISIBLE
                    adapter.Set(list)
                }
            }
            catch (e: IOException){
                GlobalScope.launch(Dispatchers.Main) {
                    val retry = view?.findViewById<Button>(R.id.event_retry_connection)
                    retry?.setOnClickListener{
                        pageNumber = 1
                        view?.findViewById<ProgressBar>(R.id.event_progressBar)?.visibility = View.VISIBLE
                        view?.findViewById<Button>(R.id.event_retry_connection)?.visibility = View.INVISIBLE
                        OnClickRetryConn(pageNumber++)
                    }
                    view?.findViewById<ProgressBar>(R.id.event_progressBar)?.visibility = View.INVISIBLE
                    retry?.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun OnClickRetryConn(pageNum: Int){
        setData(pageNum)
    }

    private fun setRecyclerViewScrollListener() {
        event_recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linLayoutMan = event_recyclerView.layoutManager as LinearLayoutManager?
                if (linLayoutMan?.findLastCompletelyVisibleItemPosition() == list.size - 1){
                    setData(++pageNumber)
                }
            }
        })
    }
}