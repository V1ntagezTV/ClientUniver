package com.example.testings.ui.services

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.testings.R

class ServicesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_services, container, false)
        root.findViewById<CardView>(R.id.serv_own_cab)?.setOnClickListener { openNewTabWindow("https://cabinet.sibsu.ru/") }
        root.findViewById<CardView>(R.id.serv_lib_lan)?.setOnClickListener { openNewTabWindow("https://e.lanbook.com/") }
        root.findViewById<CardView>(R.id.serv_lib_bashgu)?.setOnClickListener { openNewTabWindow("https://elib.bashedu.ru/") }
        root.findViewById<CardView>(R.id.serv_lib_biblio)?.setOnClickListener { openNewTabWindow("http://biblioclub.ru/") }
        return root
    }

    private fun openNewTabWindow(urls: String) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        view?.context?.startActivity(intents)
    }
}