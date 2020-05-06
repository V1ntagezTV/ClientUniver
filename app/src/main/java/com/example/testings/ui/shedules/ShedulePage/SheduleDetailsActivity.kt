package com.example.testings.ui.shedules.ShedulePage

import android.os.Bundle
import android.os.RecoverySystem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.r0adkll.slidr.Slidr

class SheduleDetailsActivity: Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shedule_details, container, false)
        recyclerView = root.findViewById(R.id.shedule_det_recyclerView)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SheduleAdapter()
        adapter.list.add(SheduleModel(201, "Механика", "Кунафин Инсур Айдарович", "20:30", "20:30", "25-51"))
        adapter.list.add(SheduleModel(201, "Механика", "Кунафин Инсур Айдарович", "20:30", "20:30", "25-51"))
        adapter.list.add(SheduleModel(201, "Механика", "Кунафин Инсур Айдарович", "20:30", "20:30", "25-51"))
        adapter.list.add(SheduleModel(201, "Механика", "Кунафин Инсур Айдарович", "20:30", "20:30", "25-51"))
        adapter.list.add(SheduleModel(201, "Механика", "Кунафин Инсур Айдарович", "20:30", "20:30", "25-51"))
        adapter.list.add(SheduleModel(201, "Механика", "Кунафин Инсур Айдарович", "20:30", "20:30", "25-51"))
        adapter.list.add(SheduleModel(201, "Механика", "Кунафин Инсур Айдарович", "20:30", "20:30", "25-51"))
        adapter.list.add(SheduleModel(201, "Механика", "Кунафин Инсур Айдарович", "20:30", "20:30", "25-51"))
        adapter.list.add(SheduleModel(201, "Механика", "Кунафин Инсур Айдарович", "20:30", "20:30", "25-51"))
        adapter.list.add(SheduleModel(201, "Механика", "Кунафин Инсур Айдарович", "20:30", "20:30", "25-51"))
        adapter.list.add(SheduleModel(201, "Механика", "Кунафин Инсур Айдарович", "20:30", "20:30", "25-51"))
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter
        return root
    }



}