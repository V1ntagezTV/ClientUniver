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


class NewsDetailsFragment: Fragment(){
    private var ContentText: String = ""
    private var ImageLinkArray: ArrayList<String> = ArrayList()
    private var linkPage: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_news_details, container, false)
        linkPage = getLinkOrCloseFragment()
        getPageContent()

        return root
    }

    private fun getLinkOrCloseFragment(): String{
        if (arguments?.getString("link") != null){
            return arguments?.getString("link") as String
        }
        activity?.onBackPressed()
        return ""
    }

    fun getPageContent(){
        GlobalScope.launch {
            val doc: Document = Jsoup.connect(linkPage).get()
            val elements = doc.select("div[class=entry-content clearfix single-post-content]")
            for (el in elements){
                GlobalScope.launch(Dispatchers.Main) {
                    val r = el.text()+'\n' + view?.findViewById<TextView>(R.id.urlnewsDetails)?.text
                    view?.findViewById<TextView>(R.id.urlnewsDetails)?.text = r
                }
            }

            val galleryImageList = elements
                .select("div[id=gallery-2]")
                .select("figure[class=gallery-item]")
            //start news images
            for (num in 0 until galleryImageList.size){
                val inLink = galleryImageList
                    .select("div[class=gallery-icon landscape]")
                    .select("a")
                    .eq(num)
                    .attr("href")

                GlobalScope.launch(Dispatchers.Main) {
                    val r = inLink + '\n' + view?.findViewById<TextView>(R.id.urlImageText)?.text
                    view?.findViewById<TextView>(R.id.urlImageText)?.text = r
                }
            }
            //
        }
    }
}