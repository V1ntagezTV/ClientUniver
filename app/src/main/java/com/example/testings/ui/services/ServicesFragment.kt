package com.example.testings.ui.services

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.testings.R
import com.example.testings.webview.WebViewActivity

class ServicesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_services, container, false)
        root.findViewById<Button>(R.id.serv_own_cab)
            ?.setOnClickListener { openNewTabWindow("https://cabinet.sibsu.ru/") }
        root.findViewById<Button>(R.id.serv_lib_lan)
            ?.setOnClickListener { openNewTabWindow("https://lanbook.com/") }
        root.findViewById<Button>(R.id.serv_lib_bashgu)
            ?.setOnClickListener { openNewTabWindow("https://elib.bashedu.ru/") }
        root.findViewById<Button>(R.id.serv_lib_biblio)
            ?.setOnClickListener { openNewTabWindow("http://biblioclub.ru/") }
        root.findViewById<Button>(R.id.serv_shedule_control)
            ?.setOnClickListener { openNewTabWindow("http://mysibsu.ru/") }
        return root
    }

    private fun openNewTabWindow(urls: String) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra("link", urls)
        startActivity(intent, Bundle())
    }

}