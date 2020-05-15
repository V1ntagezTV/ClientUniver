package com.example.testings.ui.events

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testings.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.IOException

class EventsFragment: Fragment(){

    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var eventRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: EventsAdapter
    private lateinit var retryButton: Button
    private lateinit var progressBar: ProgressBar
    private var list: ArrayList<EventModel> = ArrayList()
    private var pageNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = EventsAdapter(findNavController())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val root = inflater.inflate(R.layout.fragment_events, container, false)
        initRecyclerView(root)
        retryButton = root.findViewById(R.id.event_retry_connection)
        progressBar = root.findViewById(R.id.event_progressBar)
        eventRefreshLayout = root.findViewById(R.id.event_swipe_refresh)
        initAllListeners()
        if (adapter.itemCount == 0 ) {
            progressBar.visibility = View.VISIBLE
            retryButton.visibility = View.INVISIBLE
            setData(pageNumber++)
        }
        return root
    }

    private fun initAllListeners(){
        eventRefreshLayout.setOnRefreshListener {
            adapter.cleanList()
            list.clear()
            adapter.notifyDataSetChanged()
            pageNumber = 1
            setData(pageNumber++)
        }
        retryButton.setOnClickListener{
            adapter.cleanList()
            list.clear()
            adapter.notifyDataSetChanged()
            pageNumber = 1
            progressBar.visibility = View.VISIBLE
            retryButton.visibility = View.INVISIBLE
            setData(pageNumber++)
        }
    }

    private fun initRecyclerView(view: View){
        eventRecyclerView = view.findViewById(R.id.event_recyclerView)
        eventRecyclerView.setItemViewCacheSize(20)
        eventRecyclerView.adapter = adapter
        eventRecyclerView.itemAnimator = DefaultItemAnimator()
        eventRecyclerView.layoutManager = LinearLayoutManager(context)
        setRecyclerViewScrollListener()
    }

    private fun setData(pageNum: Int) {
        GlobalScope.launch {
            try {
                val doc = Jsoup
                    .connect("http://sibsu.ru/category/objavlenija/page/${pageNum}")
                    .get()
                val content = doc
                    .select("div[class=listing listing-blog listing-blog-1 clearfix  columns-1]")
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
                    progressBar.visibility = View.INVISIBLE
                    retryButton.visibility = View.INVISIBLE
                    adapter.addList(list)
                    if (eventRefreshLayout.isRefreshing){
                        eventRefreshLayout.isRefreshing = false
                    }
                }
            }
            catch (e: IOException){
                GlobalScope.launch(Dispatchers.Main) {
                    progressBar.visibility = View.INVISIBLE
                    retryButton.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setRecyclerViewScrollListener() {
        eventRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linLayoutMan = eventRecyclerView.layoutManager as LinearLayoutManager?
                if (linLayoutMan?.findLastCompletelyVisibleItemPosition() == list.size - 1){
                    setData(pageNumber++)
                }
            }
        })
    }
}