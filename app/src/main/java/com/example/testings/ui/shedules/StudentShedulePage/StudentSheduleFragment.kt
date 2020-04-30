package com.example.testings.ui.shedules.StudentShedulePage

import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.example.testings.ui.shedules.GroupModel
import com.example.testings.ui.shedules.LessonNum
import com.example.testings.ui.shedules.SheduleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class StudentSheduleFragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: StudentSheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shedule_student, container, false)
        recyclerView = root.findViewById(R.id.shedule_recyclerView)
        adapter = StudentSheduleAdapter()
        val groupArray = ArrayList<GroupModel>()
        groupArray.add(GroupModel("Технология", 5, "Технологический"))
        adapter.list.addAll(groupArray)
        sendGetRequest()
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.notifyDataSetChanged()

        return root
    }


    fun sendGetRequest() {
        GlobalScope.launch {
            val mURL = URL("https://mysibsu.ru/Data/GetAllGroups")
            val jsonStr = mURL.readText()
            Log.i("test", jsonStr)
        }
    }
}