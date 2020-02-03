package com.example.testings.ui.about

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.testings.R
import org.w3c.dom.Text


class AboutFragment: Fragment(){



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        val vk_image = root.findViewById<ImageView>(R.id.about_vk)
        val instagram_image = root.findViewById<ImageView>(R.id.about_instagram)
        val facebook_image = root.findViewById<ImageView>(R.id.about_facebook)
        val twitter_image = root.findViewById<ImageView>(R.id.about_twitter)
        val web_site_text_title = root.findViewById<TextView>(R.id.about_web_site_title)
        val web_site_text_context = root.findViewById<TextView>(R.id.about_web_site_context)

        web_site_text_context.setOnClickListener{ openNewTabWindow("http://sibsu.ru/")}
        web_site_text_title.setOnClickListener{ openNewTabWindow("http://sibsu.ru/")}
        vk_image.setOnClickListener { openNewTabWindow("https://vk.com/sibsu_ru")}
        instagram_image.setOnClickListener { openNewTabWindow("https://www.instagram.com/sibashgu/") }
        facebook_image.setOnClickListener { openNewTabWindow("https://www.facebook.com/SiBashGU/") }
        twitter_image.setOnClickListener { openNewTabWindow("https://twitter.com/bsusibay") }
        return root
    }

    fun openNewTabWindow(urls: String) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        view?.context?.startActivity(intents)
    }
}