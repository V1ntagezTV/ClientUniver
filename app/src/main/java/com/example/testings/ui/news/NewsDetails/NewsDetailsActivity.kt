package com.example.testings.ui.news.NewsDetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testings.R
import com.example.testings.ui.news.ImageActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException


class NewsDetailsActivity: AppCompatActivity(){

    private var ImageLinksArray: ArrayList<String> = ArrayList()
    private var linkPage: String = ""
    private var titleImage: String = ""
    private var title: String = ""
    private var contentText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_news_details)
        linkPage = getLinkOrCloseFragment()
        val toolbar = supportActionBar
        toolbar?.title = "Детали"
        toolbar?.setDisplayHomeAsUpEnabled(true)
        setPageContent()
        findViewById<Button>(R.id.newsDet_retry_connection)
            .setOnClickListener{
                findViewById<Button>(R.id.newsDet_retry_connection)?.visibility = View.INVISIBLE
                setPageContent()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getLinkOrCloseFragment(): String{
        val intent = getIntent()
        if (intent.getStringExtra("link") != null){
            return intent.getStringExtra("link").toString()
        }
        onBackPressed()
        return ""
    }

    private fun setPageContent(){
        //TODO: Рефакторить функцию
        //TODO: добавить кнопки "поделиться"
        //TODO: добавить "открыть по ссылке"
        //TODO: добавить "скопировать ссылку" на картинках и на страницах
        findViewById<Button>(R.id.newsDet_retry_connection)?.visibility = View.INVISIBLE
        findViewById<ProgressBar>(R.id.newsDet_progressBar)?.visibility = View.VISIBLE
        GlobalScope.launch {
            try {
                val doc: Document = Jsoup.connect(linkPage).get()
                val content = doc.select("div[class=entry-content clearfix single-post-content]")
                val header = doc.select("div[class=post-header post-tp-1-header]")
                val galleryImageList = content
                    .select("div[id=gallery-2]")
                    .select("figure[class=gallery-item]")

                title = header
                    .select("span[class=post-title]")
                    .text()

                titleImage = header
                    .select("div[class=single-featured]")
                    .select("img").attr("data-src")
                    .replace("-750x430", "")

                contentText += content.text().replace("Оцените материал!", "")

                var ImagesLink = ""
                for (num in 0 until galleryImageList.size){
                    val inLink = galleryImageList
                        .select("div[class=gallery-icon landscape]")
                        .select("a")
                        .eq(num)
                        .attr("href")
                    ImageLinksArray.add(inLink)
                    ImagesLink += inLink + '\n'
                }

                GlobalScope.launch(Dispatchers.Main) {
                    findViewById<ProgressBar>(R.id.newsDet_progressBar)?.visibility = View.INVISIBLE
                    findViewById<TextView>(R.id.newsDet_title)?.text = title
                    findViewById<View>(R.id.newsDet_lineUpImageTitle)?.visibility = View.VISIBLE
                    findViewById<View>(R.id.newsDet_lineUnderImageTitle)?.visibility = View.VISIBLE
                    findViewById<View>(R.id.newsDet_lineUpFooter)?.visibility = View.VISIBLE
                    Picasso.get().load(titleImage)
                        .into(findViewById<ImageView>(R.id.newsDet_titleImage))
                    findViewById<TextView>(R.id.newsDet_content)?.text = contentText
                    findViewById<TextView>(R.id.newsDet_imageurls)?.text = ImagesLink
                }
            }
            catch (e: IOException){
                GlobalScope.launch(Dispatchers.Main) {
                    findViewById<ProgressBar>(R.id.newsDet_progressBar)?.visibility = View.INVISIBLE
                    findViewById<Button>(R.id.newsDet_retry_connection)?.visibility = View.VISIBLE
                }
            }
        }
    }

    fun OnClickImageNews(v: View){
        val intent = Intent(v.context, ImageActivity::class.java)
        intent.putExtra("ImageUrl", titleImage)
        startActivity(intent, Bundle())
    }
}