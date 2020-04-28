package com.example.testings.ui.faculties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.example.testings.ui.news.NewsAdapter

class FacultiesFragment : Fragment() {

    lateinit var facultyRecyclerView: RecyclerView
    lateinit var adapter: FacultiesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_faculties, container, false)
        initRecyclerView(root)
        adapter.Set(FacultyMocks.getContent())
        return root
    }

    private fun initRecyclerView(view: View){
        facultyRecyclerView = view.findViewById(R.id.faculty_recyclerView)
        facultyRecyclerView.setItemViewCacheSize(20) //временное решение cache uses for 20 newsview
        adapter = FacultiesAdapter()
        facultyRecyclerView.adapter = adapter
        facultyRecyclerView.itemAnimator = DefaultItemAnimator()
        facultyRecyclerView.layoutManager = LinearLayoutManager(context)
    }
}