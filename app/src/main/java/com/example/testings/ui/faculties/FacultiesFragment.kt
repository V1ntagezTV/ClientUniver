package com.example.testings.ui.faculties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R

class FacultiesFragment : Fragment() {

    lateinit var facultyRecyclerView: RecyclerView
    lateinit var adapter: FacultiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FacultiesAdapter(findNavController())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_faculties, container, false)
        initRecyclerView(root)
        adapter.Set(FacultyMocks.getContent())
        return root
    }

    private fun initRecyclerView(view: View){
        facultyRecyclerView = view.findViewById(R.id.faculty_recyclerView)
        facultyRecyclerView.adapter = adapter
        facultyRecyclerView.itemAnimator = DefaultItemAnimator()
        facultyRecyclerView.layoutManager = LinearLayoutManager(context)
    }
}