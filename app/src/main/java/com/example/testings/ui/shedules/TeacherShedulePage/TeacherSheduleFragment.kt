package com.example.testings.ui.shedules.TeacherShedulePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class TeacherSheduleFragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: TeacherAdapter
    lateinit var retry: Button
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TeacherAdapter(findNavController())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shedule_teacher, container, false)
        recyclerView = root.findViewById(R.id.teachers_recyclerView)
        retry = root.findViewById(R.id.teachers_retry_connection)
        progressBar = root.findViewById(R.id.teachers_progressBar)
        initRecyclerView()
        initListeners()
        if (adapter.itemCount == 0){
            sendGetRequest()
        }
        return root
    }

    private fun initListeners(){
        retry.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            retry.visibility = View.INVISIBLE
            sendGetRequest()
        }
    }

    private fun initRecyclerView(){
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun sendGetRequest(){
        progressBar.visibility = View.VISIBLE
        retry.visibility = View.INVISIBLE
        GlobalScope.launch {
            try {
                val link = URL("https://mysibsu.ru/Data/GetAllTeachers")
                val array = JSONArray(link.readText())
                for (ind in 0 until array.length()){
                    val obj = JSONObject(array[ind].toString())
                    val model = TeacherModel(
                        obj.getInt("TeacherId"),
                        obj.getString("FirstName"),
                        obj.getString("LastName"),
                        obj.getString("MiddleName")
                    )
                    GlobalScope.launch(Dispatchers.Main) {
                        retry.visibility = View.INVISIBLE
                        progressBar.visibility = View.INVISIBLE
                        adapter.list.add(model)
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (ex: IOException) {
                GlobalScope.launch(Dispatchers.Main) {
                    retry.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}