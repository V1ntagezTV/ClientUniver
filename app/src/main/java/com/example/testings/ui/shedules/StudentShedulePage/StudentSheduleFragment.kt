package com.example.testings.ui.shedules.StudentShedulePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class StudentSheduleFragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: StudentSheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shedule_student, container, false)
        recyclerView = root.findViewById(R.id.students_recyclerView)
        adapter = StudentSheduleAdapter()
        this.initRecyclerView()
        this.sendGetRequest()


        return root
    }
    private fun initRecyclerView(){
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context)
    }


    private fun sendGetRequest() {
        GlobalScope.launch {
            val mURL = URL("https://mysibsu.ru/Data/GetAllGroups")
            val array = JSONArray(mURL.readText())
            for (ind in 0 until array.length()){
                val obj = JSONObject(array[ind].toString())
                val model = GroupModel(
                    obj.getInt("GroupId"),
                    obj.getString("TeachProfile"),
                    obj.getInt("Cours"),
                    obj.getString("Faculty")
                )
                GlobalScope.launch(Dispatchers.Main) {
                    adapter.list.add(model)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}
// [{"GroupId":1,"Cours":4,"Faculty":"Технологический","TeachProfile":"Оч. Технология"}]


















