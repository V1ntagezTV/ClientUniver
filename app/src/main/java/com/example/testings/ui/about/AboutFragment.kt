package com.example.testings.ui.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.testings.R
import com.example.testings.webview.WebViewActivity


class AboutFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        val vk_image = root.findViewById<ImageView>(R.id.about_vk)
        val instagram_image = root.findViewById<ImageView>(R.id.about_instagram)
        val facebook_image = root.findViewById<ImageView>(R.id.about_facebook)
        val twitter_image = root.findViewById<ImageView>(R.id.about_twitter)
        val website = root.findViewById<CardView>(R.id.about_website)
        val callnumber = root.findViewById<CardView>(R.id.about_callnumber)

        callnumber.setOnClickListener {
            val phone = "8 347 755 15 70"
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }
        website.setOnClickListener { openWebViewActivity("http://sibsu.ru/", context) }
        vk_image.setOnClickListener { openWebViewActivity("https://vk.com/sibsu_ru", context)}
        instagram_image.setOnClickListener { openWebViewActivity("https://www.instagram.com/sibashgu/", context) }
        facebook_image.setOnClickListener { openWebViewActivity("https://www.facebook.com/SiBashGU/", context) }
        twitter_image.setOnClickListener { openWebViewActivity("https://twitter.com/bsusibay", context) }
        return root
    }

    private fun openWebViewActivity(urls: String, context: Context?) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra("link", urls)
        startActivity(intent, Bundle())
    }
}