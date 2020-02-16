package com.example.testings.ui.news.NewsDetails


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.testings.R
import com.example.testings.ImageActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import com.example.testings.Addons.ExpandableHeightGridView


class NewsDetailsActivity: AppCompatActivity() {

    private var ImageLinksArray: ArrayList<String> = ArrayList()
    private var linkPage: String = ""
    private var titleImage: String = ""
    private var title: String = ""
    private var contentText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_news_details)
        val toolbar = supportActionBar
        toolbar?.title = "Детали"
        toolbar?.setDisplayHomeAsUpEnabled(true)
        linkPage = getLinkOrCloseActivity()
        setPageContent()

        findViewById<Button>(R.id.newsDet_retry_connection).setOnClickListener{
            findViewById<Button>(R.id.newsDet_retry_connection).visibility = View.INVISIBLE
            setPageContent()
        }
        findViewById<ImageView>(R.id.newsDet_titleImage).setOnClickListener{
            OnClickImage(titleImage)
        }
        findViewById<ExpandableHeightGridView>(R.id.newsDet_gridImages).setOnItemClickListener { _, _, position, _ ->
                    OnClickImage(ImageLinksArray[position])
        }
    }

    private fun getLinkOrCloseActivity(): String{
        val intent = getIntent()
        if (intent.getStringExtra("link") != null){
            return intent.getStringExtra("link")
        }
        onBackPressed()
        return ""
    }

    private fun setPageContent(){
        //TODO: Рефакторить функцию
        //TODO: добавить "открыть по ссылке"
        //TODO: добавить "скопировать ссылку" на картинках и на страницах
        //TODO: избавиться от отступов внизу
        findViewById<Button>(R.id.newsDet_retry_connection)?.visibility = View.INVISIBLE
        findViewById<ProgressBar>(R.id.newsDet_progressBar)?.visibility = View.VISIBLE

        GlobalScope.launch {
            try {
                val doc: Document = Jsoup.connect(linkPage).get()
                val content = doc
                    .select("div[class=entry-content clearfix single-post-content]")[0]
                    .children()
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

                for (num in 0 until galleryImageList.size){
                    val inLink = galleryImageList
                        .select("div")
                        .select("a")
                        .eq(num)
                        .attr("href")
                    ImageLinksArray.add(inLink)
                }

                contentText += content[0].text()
                for (num in 1 until content.size - 2) {
                    if (content[num].tag().normalName() == "div" || content[num].text() == "") continue
                    contentText += "\n\n" + content[num].text()
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
                            .into(findViewById<ImageView>(R.id.newsDet_titleImage))
                    }
                    //
                    val gridView: ExpandableHeightGridView = findViewById(R.id.newsDet_gridImages)
                    val adapter = ImageListAdapter(baseContext, ImageLinksArray)
                    gridView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    gridView.isExpanded = true

                    findViewById<ProgressBar>(R.id.newsDet_progressBar)?.visibility = View.INVISIBLE
                    //setNewsContent
                    findViewById<TextView>(R.id.newsDet_title)?.text = title
                    findViewById<TextView>(R.id.newsDet_content)?.text = contentText
                    //design content unhide
                    findViewById<View>(R.id.newsDet_lineFooter)?.visibility = View.VISIBLE
                    findViewById<LinearLayout>(R.id.newsDet_reqPanel)?.visibility = View.VISIBLE
                    findViewById<ImageView>(R.id.newsDet_reqVK)?.setOnClickListener { openNewTabWindow(vklink) }
                    findViewById<ImageView>(R.id.newsDet_reqFB)?.setOnClickListener { openNewTabWindow(fblink) }
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

    fun OnClickImage(link: String){
        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra("ImageUrl", link)
        startActivity(intent, Bundle())
    }

    private fun openNewTabWindow(urls: String) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        startActivity(intents)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}