package com.example.testings.ui.news


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.example.testings.ui.news.NewsDetails.NewsDetailsFragment
import com.squareup.picasso.Picasso



class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsHolder>(){

    private var list: ArrayList<NewsModel> = ArrayList()

    class NewsHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {

        var TViewTitle: TextView = ItemView.findViewById(R.id.news_content_title)
        var TViewDate: TextView = ItemView.findViewById(R.id.news_content_postdate)
        var TViewDescrip: TextView = ItemView.findViewById(R.id.news_content_small_desc)
        var IViewPreview: ImageView = ItemView.findViewById(R.id.news_content_imgView_avatar)
        var URLDetails = ""

        init {
            ItemView.setOnClickListener{
                val intent = Intent(itemView.context, NewsDetailsFragment::class.java)
                intent.putExtra("link", URLDetails)
                startActivity(itemView.context, intent, Bundle())
            }
        }
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
        holder.URLDetails = news.URLDetails

        Picasso.get()
            .load(news.URLPreview)
            .into(holder.IViewPreview)

        holder.IViewPreview.setOnClickListener { v: View ->
            val intent = Intent(v.context, ImageActivity::class.java)
            intent.putExtra("ImageUrl", news.URLPreview)
            startActivity(v.context, intent, Bundle())
        }
    }

    fun set(arrayList: ArrayList<NewsModel>){
        list.clear()
        list.addAll(arrayList)
        notifyDataSetChanged()
    }
}