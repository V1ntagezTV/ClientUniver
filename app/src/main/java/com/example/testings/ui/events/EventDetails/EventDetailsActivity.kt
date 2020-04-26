package com.example.testings.ui.events.EventDetails

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testings.R
import com.example.testings.ui.events.EventModel
import com.r0adkll.slidr.Slidr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.IOException


class EventDetailsActivity : AppCompatActivity(){

    private var EventPage = EventModel()
    private lateinit var retry: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Slidr.attach(this)
        setContentView(R.layout.fragment_events_details)
        retry = findViewById(R.id.eventDet_retry_connection)
        progressBar = findViewById(R.id.eventDet_progressBar)
        val linkPage = getLinkOrCloseActivity()
        val toolbar = supportActionBar
        toolbar?.title = "Детали"
        toolbar?.setDisplayHomeAsUpEnabled(true)
        setData(linkPage)
        retry.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            retry.visibility = View.INVISIBLE
            setData(linkPage)
        }
    }

    private fun setData(link: String){
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
                    findViewById<TextView>(R.id.eventDet_title).text = EventPage.Title
                    findViewById<TextView>(R.id.eventDet_content).text = EventPage.FullDescription
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
        val intent = getIntent()
        if (intent.getStringExtra("link") != null){
            return intent.getStringExtra("link") as String
        }
        onBackPressed()
        return ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}