package com.example.testings.ui.news.NewsDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.testings.R
import com.squareup.picasso.Picasso


class ImageListAdapter : BaseAdapter {
    var list = ArrayList<String>()
    var context: Context? = null

    constructor(context: Context, foodsList: ArrayList<String>) : super() {
        this.context = context
        this.list = foodsList
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val imageView = inflator.inflate(R.layout.fragment_news_details_image_content, null)
        if (this.count == 0){
            return imageView
        }
        val imageUrl = this.list[position]

        Picasso.get().load(imageUrl).into(imageView.findViewById<ImageView>(R.id.newsDet_gridView_image))

        return imageView
    }
}


