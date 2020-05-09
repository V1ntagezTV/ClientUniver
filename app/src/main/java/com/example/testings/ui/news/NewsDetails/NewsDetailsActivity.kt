package com.example.testings.ui.news.NewsDetails


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
import com.r0adkll.slidr.Slidr


class NewsDetailsActivity: Fragment() {

    private var ImageLinksArray: ArrayList<String> = ArrayList()
    lateinit var expandableHeightGridView: ExpandableHeightGridView
    lateinit var titleImageView: ImageView
    private var linkPage: String = ""
    private var titleImage: String = ""
    private var title: String = ""
    private var contentText: String = ""
    lateinit var retry: Button
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_news_details, container, false)
        linkPage = getLinkOrCloseActivity()
        retry = root.findViewById(R.id.newsDet_retry_connection)
        progressBar = root.findViewById(R.id.newsDet_progressBar)
        expandableHeightGridView = root.findViewById(R.id.newsDet_gridImages)
        titleImageView = root.findViewById(R.id.newsDet_titleImage)
        setPageContent(root)
        retry.setOnClickListener{
            root.findViewById<Button>(R.id.newsDet_retry_connection).visibility = View.INVISIBLE
            setPageContent(root)
        }
        titleImageView.setOnClickListener{
            OnClickImage(titleImage)
        }
        expandableHeightGridView.setOnItemClickListener { _, _, position, _ ->
                    OnClickImage(ImageLinksArray[position])
        }
        return root
    }

    private fun getLinkOrCloseActivity(): String{
        val link = arguments?.getString("link")
        if (link != null){
            return link
        }
        findNavController().popBackStack()
        return ""
    }

    private fun setPageContent(view: View){
        retry.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
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
                for (num in 1 until content.size) {
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
                            .into(view.findViewById<ImageView>(R.id.newsDet_titleImage))
                    }
                    //
                    val gridView: ExpandableHeightGridView = view.findViewById(R.id.newsDet_gridImages)
                    val adapter = ImageListAdapter(view.context, ImageLinksArray)
                    gridView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    gridView.isExpanded = true

                    progressBar.visibility = View.INVISIBLE
                    //setNewsContent
                    view.findViewById<TextView>(R.id.newsDet_title)?.text = title
                    view.findViewById<TextView>(R.id.newsDet_content)?.text = contentText
                    //design content unhide
                    view.findViewById<View>(R.id.newsDet_lineFooter)?.visibility = View.VISIBLE
                    view.findViewById<LinearLayout>(R.id.newsDet_reqPanel)?.visibility = View.VISIBLE
                    view.findViewById<ImageView>(R.id.newsDet_reqVK)?.setOnClickListener { openNewTabWindow(vklink) }
                    view.findViewById<ImageView>(R.id.newsDet_reqFB)?.setOnClickListener { openNewTabWindow(fblink) }
                    if (ImageLinksArray.count() == 0){
                        expandableHeightGridView.visibility = View.GONE
                    }
                }
            }
            catch (e: IOException){
                GlobalScope.launch(Dispatchers.Main) {
                    view.findViewById<ProgressBar>(R.id.newsDet_progressBar)?.visibility = View.INVISIBLE
                    view.findViewById<Button>(R.id.newsDet_retry_connection)?.visibility = View.VISIBLE
                }
            }
        }
    }

    fun OnClickImage(link: String){
        val intent = Intent(this.context, ImageActivity::class.java)
        intent.putExtra("ImageUrl", link)
        startActivity(intent, Bundle())
    }

    private fun openNewTabWindow(urls: String) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        startActivity(intents)
    }
}