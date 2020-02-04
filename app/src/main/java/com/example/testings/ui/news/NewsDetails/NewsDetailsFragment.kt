package com.example.testings.ui.news.NewsDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.testings.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.w3c.dom.Text
import java.io.IOException


class NewsDetailsFragment: Fragment(){

    private var ImageLinkArray: ArrayList<String> = ArrayList()
    private lateinit var linkPage: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_news_details, container, false)
        linkPage = getLinkOrCloseFragment()

        setPageContent()

        return root
    }

    private fun getLinkOrCloseFragment(): String{
        if (arguments?.getString("link") != null){
            return arguments?.getString("link") as String
        }
        activity?.onBackPressed()
        return ""
    }

    fun setPageContent(){
        GlobalScope.launch {
            try {
                var ContentText: String = ""
                var ContentImageUrls = ""
                val doc: Document = Jsoup.connect(linkPage).get()
                val content = doc.select("div[class=entry-content clearfix single-post-content]")
                val header = doc.select("div[class=post-header post-tp-1-header]")
                val galleryImageList = content
                    .select("div[id=gallery-2]")
                    .select("figure[class=gallery-item]")

                for (el in content){
                    ContentText += el.text() + '\n'+ '\t'
                }

                for (num in 0 until galleryImageList.size){
                    val inLink = galleryImageList
                        .select("div[class=gallery-icon landscape]")
                        .select("a")
                        .eq(num)
                        .attr("href")
                    ContentImageUrls += inLink + '\n'
                }

                GlobalScope.launch(Dispatchers.Main) {
                    view?.findViewById<TextView>(R.id.newsDet_imageurls)?.text = ContentImageUrls
                    view?.findViewById<TextView>(R.id.newsDet_content)?.text = ContentText
                }
            }
            catch (e: IOException){

            }
        }
    }
}