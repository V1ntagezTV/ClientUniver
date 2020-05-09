package com.example.testings.ui.events.EventDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.testings.R
import com.example.testings.ui.events.EventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.IOException


class EventDetailsFragment : Fragment() {

    private var EventPage = EventModel()
    private lateinit var retry: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_events_details, container, false)
        retry = root.findViewById(R.id.eventDet_retry_connection)
        progressBar = root.findViewById(R.id.eventDet_progressBar)
        val linkPage = getLinkOrCloseActivity()
        setData(linkPage, root)
        retry.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            retry.visibility = View.INVISIBLE
            setData(linkPage, root)
        }
        return root
    }

    private fun setData(link: String, view: View){
        GlobalScope.launch {
            try {
                val doc = Jsoup.connect(link).get()
                val content = doc.select("div[class=single-container]").select("article")
                EventPage.Title = content
                    .select("div[class=post-header post-tp-1-header]")
                    .select("h1[class=single-post-title]").text()
                val desc = doc
                    .select("div[class=entry-content clearfix single-post-content]")
                    .select("p")

                EventPage.FullDescription += desc[0].text()
                for(i in 1 until desc.size){
                    if (desc[i].tag().normalName() == "div" || desc[i].text() == "") continue
                    EventPage.FullDescription += "\n\n" + desc[i].text()
                }

                GlobalScope.launch(Dispatchers.Main) {
                    progressBar.visibility = View.INVISIBLE
                    retry.visibility = View.INVISIBLE
                    view.findViewById<TextView>(R.id.eventDet_title).text = EventPage.Title
                    view.findViewById<TextView>(R.id.eventDet_content).text = EventPage.FullDescription
                }
            }
            catch (e: IOException){
                GlobalScope.launch(Dispatchers.Main) {
                    retry.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun getLinkOrCloseActivity(): String{
        val link = arguments?.getString("link")
        if (link != null){
            return link
        }
        return ""
    }
}