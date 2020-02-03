package com.example.testings.ui.shedules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R

class SheduleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shedule, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.shedule_recyclerView)
        val sheduleArray = ArrayList<SheduleModel>()
        sheduleArray.add(SheduleModel("Механика", 201, "Валеев А.С", LessonNum.firFirst))
        sheduleArray.add(SheduleModel("Педагогика", 201, "Валеева Г.Х", LessonNum.firSecond))
        sheduleArray.add(SheduleModel("ТиМОТ", 201, "Валеева Г.Х", LessonNum.firThird))
        val adapter = SheduleAdapter(sheduleArray)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.notifyDataSetChanged()

        return root
    }
}