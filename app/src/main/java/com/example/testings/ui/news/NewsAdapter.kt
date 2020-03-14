package com.example.testings.ui.news


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.ImageActivity
import com.example.testings.R
import com.example.testings.ui.news.NewsDetails.NewsDetailsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsHolder>(){

    private var list: ArrayList<NewsModel> = ArrayList()

    class NewsHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {

        var TViewTitle: TextView = ItemView.findViewById(R.id.news_content_title)
        var TViewDate: TextView = ItemView.findViewById(R.id.news_content_postdate)
        var TViewDescrip: TextView = ItemView.findViewById(R.id.news_content_small_desc)
        var IViewPreview: ImageView = ItemView.findViewById(R.id.news_content_imgView_avatar)
        var URLDetails: TextView = ItemView.findViewById(R.id.news_content_linkTextView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_news_item_content, parent, false)
        return NewsHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val news = list[position]
        holder.TViewTitle.text = news.Title
        holder.TViewDate.text = news.PostDate
        holder.TViewDescrip.text = news.SmallDescription
        holder.URLDetails.text = news.URLDetails


        if (news.URLPreview.startsWith("http://")) {
            Picasso.get()
                .load(news.URLPreview)
                .into(holder.IViewPreview)
        }

        holder.IViewPreview.setOnClickListener { v: View ->
            val intent = Intent(v.context, ImageActivity::class.java)
            intent.putExtra("ImageUrl", news.URLPreview)
            startActivity(v.context, intent, Bundle())
        }

        if (news.URLDetails.startsWith("http://sibsu.ru/novosti/")){
            holder.itemView.setOnClickListener{ v: View ->
                val intent = Intent(v.context, NewsDetailsActivity::class.java)
                intent.putExtra("link", news.URLDetails)
                startActivity(v.context, intent, Bundle())
            }
        } else {
            holder.itemView.findViewById<View>(R.id.news_content_linkview).visibility = View.VISIBLE
            holder.itemView.findViewById<TextView>(R.id.news_content_linkTextView).visibility = View.VISIBLE
            holder.itemView.setOnClickListener{ v: View ->
                val urlis = Uri.parse(news.URLDetails)
                val intents = Intent(Intent.ACTION_VIEW, urlis)
                startActivity(v.context, intents, Bundle())
            }
        }
    }

    fun Set(arrayList: ArrayList<NewsModel>){
        list.clear()
        list.addAll(arrayList)
        notifyDataSetChanged()
    }

    private fun openNewTabWindow(urls: String, context: Context) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        startActivity(context, intents, Bundle())
    }
}