package com.example.testings.ui.shedules.ShedulePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class SheduleDetailsActivity: Fragment() {

    var weekOfDate: Int = 0
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SheduleAdapter
    private lateinit var sheduleList: ArrayList<ArrayList<SheduleModel>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SheduleAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shedule_details, container, false)
        recyclerView = root.findViewById(R.id.shedule_det_recyclerView)
        sheduleList = ArrayList(6)
        sheduleList.add(0, arrayListOf())
        sheduleList.add(1, arrayListOf())
        sheduleList.add(2, arrayListOf())
        sheduleList.add(3, arrayListOf())
        sheduleList.add(4, arrayListOf())
        sheduleList.add(5, arrayListOf())
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context)
        initListeners(root)
        val cal = Calendar.getInstance()
        sendGetRequest(1, "teacher")
        weekOfDate = cal.get(Calendar.DAY_OF_WEEK)
        when (weekOfDate) {
            Calendar.MONDAY -> adapter.addList(sheduleList[0])
            Calendar.TUESDAY -> adapter.addList(sheduleList[1])
            Calendar.WEDNESDAY -> adapter.addList(sheduleList[2])
            Calendar.THURSDAY -> adapter.addList(sheduleList[3])
            Calendar.FRIDAY -> adapter.addList(sheduleList[4])
            Calendar.SATURDAY -> adapter.addList(sheduleList[5])
        }
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter
        return root
    }

    private fun initListeners(view: View){
        view.findViewById<Chip>(R.id.Mon).setOnClickListener{
            adapter.list.clear()
            adapter.list.addAll(sheduleList[0])
            adapter.notifyDataSetChanged()
        }
        view.findViewById<Chip>(R.id.Tue).setOnClickListener{
            adapter.list.clear()
            adapter.list.addAll(sheduleList[1])
            adapter.notifyDataSetChanged()
        }
        view.findViewById<Chip>(R.id.Wed).setOnClickListener{
            adapter.list.clear()
            adapter.list.addAll(sheduleList[2])
            adapter.notifyDataSetChanged()
        }
        view.findViewById<Chip>(R.id.Thu).setOnClickListener{
            adapter.list.clear()
            adapter.list.addAll(sheduleList[3])
            adapter.notifyDataSetChanged()
        }
        view.findViewById<Chip>(R.id.Fri).setOnClickListener{
            adapter.list.clear()
            adapter.list.addAll(sheduleList[4])
            adapter.notifyDataSetChanged()
        }
        view.findViewById<Chip>(R.id.Sat).setOnClickListener{
            adapter.list.clear()
            adapter.list.addAll(sheduleList[5])
            adapter.notifyDataSetChanged()
        }

    }

    private fun sendGetRequest(id: Int, personType: String) {
        GlobalScope.launch {
            try {
                val mURL = URL("https://mysibsu.ru/Data/GetSheduleById?id=${id}&type=${personType}")
                val dayOfWeekShedule = JSONArray(mURL.readText())

                for (dayInd in 0 until dayOfWeekShedule.length()) {
                    val sheduleArrayInDay = dayOfWeekShedule.getJSONArray(dayInd)

                    for (sheduleInd in 0 until sheduleArrayInDay.length()){
                        val lesson = sheduleArrayInDay[sheduleInd] as JSONObject
                        val teacher = lesson.getJSONObject("Teacher")
                        val teacherFullName = teacher.getString("LastName") + " " + teacher.getString("FirstName") + " " + teacher.getString("MiddleName")
                        val lessonInfo = lesson.getJSONObject("LessonInfo")
                        val data = SheduleModel(
                            lesson.getInt("LessonId"),
                            lessonInfo.getInt("AuditionNum"),
                            lessonInfo.getString("LessName"),
                            teacherFullName,
                            lessonInfo.getString("StartTime"),
                            lessonInfo.getString("EndTime"),
                            lessonInfo.getString("WeekDateNumbers"))
                        GlobalScope.launch(Dispatchers.Main) {
                            sheduleList[dayInd].add(data)
                        }

                    }
                }

            } catch (ex: IOException){

            }
        }
    }

}