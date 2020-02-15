package com.example.testings.ui.events

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testings.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.IOException


class EventDetailsActivity : AppCompatActivity(){

    private var EventPage = EventModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_events_details)
        val linkPage = getLinkOrCloseActivity()
        val toolbar = supportActionBar
        toolbar?.title = "Детали"
        toolbar?.setDisplayHomeAsUpEnabled(true)
        setData(linkPage)
    }
    //set in EventPage !content!
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
                for(i in 0 until desc.size){
                    if (i == desc.size - 2) {
                        EventPage.FullDescription += desc[i].text()
                        break
                    }
                    EventPage.FullDescription += desc[i].text() + "\n\n"
                }
                GlobalScope.launch(Dispatchers.Main) {
                    findViewById<TextView>(R.id.eventDet_title).text = EventPage.Title
                    findViewById<TextView>(R.id.eventDet_content).text = EventPage.FullDescription
                }
            }
            catch (e: IOException){

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