package com.example.testings.ui.shedules.TeacherShedulePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R

class TeacherSheduleFragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    val adapter: TeacherAdapter = TeacherAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shedule_teacher, container, false)
        recyclerView = root.findViewById(R.id.teachers_recyclerView)
        initRecyclerView()
        adapter.list.add(TeacherModel(1, "Кунафин", "Инсур", "Айдарович"))
        adapter.notifyDataSetChanged()
        return root
    }

    private fun initRecyclerView(){
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}