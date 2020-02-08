package com.example.testings.ui.news.NewsDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.testings.R
import com.example.testings.ImageActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException


class NewsDetailsFragment: Fragment(){

    private var ImageLinksArray: ArrayList<String> = ArrayList()
    private var linkPage: String = ""
    private var titleImage: String = ""
    private var title: String = ""
    private var contentText: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_news_details, container, false)
        linkPage = getLinkOrCloseFragment()
        setPageContent()
        root.findViewById<Button>(R.id.newsDet_retry_connection)?.setOnClickListener{
            root.findViewById<Button>(R.id.newsDet_retry_connection)?.visibility = View.INVISIBLE
            setPageContent()
        }
        root.findViewById<ImageView>(R.id.newsDet_titleImage)?.setOnClickListener{
            OnClickImageNews()
        }
        return root
    }

    private fun getLinkOrCloseFragment(): String{
        if (arguments?.getString("link") != null){
            return arguments?.getString("link") as String
        }
        activity?.onBackPressed()
        return ""
    }

    private fun setPageContent(){
        //TODO: Рефакторить функцию
        //TODO: добавить "открыть по ссылке"
        //TODO: добавить "скопировать ссылку" на картинках и на страницах
        view?.findViewById<Button>(R.id.newsDet_retry_connection)?.visibility = View.INVISIBLE
        view?.findViewById<ProgressBar>(R.id.newsDet_progressBar)?.visibility = View.VISIBLE
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

                //start links to response
                val vklink = doc
                    .select("div[class=share-handler-wrap bs-pretty-tabs-initialized]")
                    .select("span[class=social-item vk]")
                    .select("a").attr("href")

                val fblink = doc.select("div[class=share-handler-wrap bs-pretty-tabs-initialized]")
                    .select("span[class=social-item facebook]")
                    .select("a").attr("href")
                //end links to responce

                GlobalScope.launch(Dispatchers.Main) {
                    if (titleImage.startsWith("http://")){
                        Picasso.get()
                            .load(titleImage)
                            .into(view?.findViewById<ImageView>(R.id.newsDet_titleImage))
                    }
                    view?.findViewById<ProgressBar>(R.id.newsDet_progressBar)?.visibility = View.INVISIBLE

                    view?.findViewById<TextView>(R.id.newsDet_title)?.text = title
                    view?.findViewById<TextView>(R.id.newsDet_content)?.text = contentText
                    view?.findViewById<TextView>(R.id.newsDet_imageurls)?.text = ImagesLink

                    view?.findViewById<LinearLayout>(R.id.newsDet_reqPanel)?.visibility = View.VISIBLE
                    view?.findViewById<ImageView>(R.id.newsDet_reqVK)?.setOnClickListener { openNewTabWindow(vklink) }
                    view?.findViewById<ImageView>(R.id.newsDet_reqFB)?.setOnClickListener { openNewTabWindow(fblink) }
                }
            }
            catch (e: IOException){
                GlobalScope.launch(Dispatchers.Main) {
                    view?.findViewById<ProgressBar>(R.id.newsDet_progressBar)?.visibility = View.INVISIBLE
                    view?.findViewById<Button>(R.id.newsDet_retry_connection)?.visibility = View.VISIBLE
                }
            }
        }
    }

    fun OnClickImageNews(){
        val intent = Intent(view?.context, ImageActivity::class.java)
        intent.putExtra("ImageUrl", titleImage)
        startActivity(intent, Bundle())
    }

    private fun openNewTabWindow(urls: String) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        view?.context?.startActivity(intents)
    }
}