package com.example.testings.ui.news.NewsDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.testings.R


class NewsDetailsFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_news_details, container, false)

        val link: String? = arguments?.getString("link")
        root.findViewById<TextView>(R.id.urlnewsDetails).text = link

        return root
    }
}