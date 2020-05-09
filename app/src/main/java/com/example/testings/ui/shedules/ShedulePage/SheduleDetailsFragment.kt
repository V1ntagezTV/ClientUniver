package com.example.testings.ui.shedules.ShedulePage

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
import kotlin.reflect.typeOf

class SheduleDetailsFragment: Fragment() {

    val SHEDULE_DAYS: Int = 6
    var weekOfDate: Int = 0
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SheduleAdapter
    private lateinit var sheduleList: ArrayList<ArrayList<SheduleModel>>
    lateinit var retry: Button
    lateinit var progressBar: ProgressBar
    lateinit var day: TextView

    override fun setEnterTransition(transition: Any?) {
        super.setEnterTransition(transition)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SheduleAdapter()
        sheduleList = ArrayList(this.SHEDULE_DAYS)
        for (i in 0 until this.SHEDULE_DAYS){
            sheduleList.add(i, arrayListOf())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shedule_details, container, false)

        recyclerView = root.findViewById(R.id.shedule_det_recyclerView)
        retry = root.findViewById(R.id.shedule_det_retry)
        progressBar = root.findViewById(R.id.shedule_det_progressBar)
        day = root.findViewById(R.id.shedule_det_day)
        initRecyclerView()
        sendGetRequest(SheduleModelView.currentId, SheduleModelView.currentType, root)
        initListeners(root)
        return root
    }

    private fun setTodaySheduleList(view: View) {
        val cal = Calendar.getInstance()
        weekOfDate = cal.get(Calendar.DAY_OF_WEEK)
        when (weekOfDate) {
            Calendar.MONDAY -> {
                adapter.addList(sheduleList[0])
                day.text = "Понедельник"
            }
            Calendar.TUESDAY -> {
                adapter.addList(sheduleList[1])
                day.text = "Вторник"
            }
            Calendar.WEDNESDAY -> {
                adapter.addList(sheduleList[2])
                day.text = "Среда"
            }
            Calendar.THURSDAY -> {
                adapter.addList(sheduleList[3])
                day.text = "Четверг"
            }
            Calendar.FRIDAY -> {
                adapter.addList(sheduleList[4])
                day.text = "Пятница"
            }
            Calendar.SATURDAY -> {
                adapter.addList(sheduleList[5])
                day.text = "Суббота"
            }
        }
    }

    private fun initRecyclerView(){
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun initListeners(view: View){
        retry.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            retry.visibility = View.INVISIBLE
            sendGetRequest(SheduleModelView.currentId, SheduleModelView.currentType, view)
        }
        view.findViewById<Chip>(R.id.Mon).setOnClickListener{
            adapter.addList(sheduleList[0])
        }
        view.findViewById<Chip>(R.id.Tue).setOnClickListener{
            adapter.addList(sheduleList[1])
        }
        view.findViewById<Chip>(R.id.Wed).setOnClickListener{
            adapter.addList(sheduleList[2])
        }
        view.findViewById<Chip>(R.id.Thu).setOnClickListener{
            adapter.addList(sheduleList[3])
        }
        view.findViewById<Chip>(R.id.Fri).setOnClickListener{
            adapter.addList(sheduleList[4])
        }
        view.findViewById<Chip>(R.id.Sat).setOnClickListener{
            adapter.addList(sheduleList[5])
        }
    }

    private fun sendGetRequest(id: Int, personType: String, view: View) {
        progressBar.visibility = View.VISIBLE
        retry.visibility = View.INVISIBLE
        GlobalScope.launch {
            try {
                val mURL = URL("https://mysibsu.ru/Data/GetSheduleById?id=${id}&type=${personType}")
                val dayOfWeekShedule = JSONArray(mURL.readText())

                for (dayInd in 0 until dayOfWeekShedule.length()) {
                    val sheduleArrayInDay = dayOfWeekShedule.getJSONArray(dayInd)
                    for (sheduleInd in 0 until sheduleArrayInDay.length()){
                        val lesson = sheduleArrayInDay[sheduleInd] as JSONObject
                        val teacher = lesson.getJSONObject("Teacher")
                        val group = lesson.getJSONObject("Group")
                        val lessonInfo = lesson.getJSONObject("LessonInfo")
                        val teacherFullName =
                                teacher.getString("LastName") + " " +
                                teacher.getString("FirstName") + " " +
                                teacher.getString("MiddleName")
                        /** time string format from json 2020-01-01T00:00:00 **/
                        val startTime = lessonInfo.getString("StartTime").substring(11, 16)
                        val endTime = lessonInfo.getString("EndTime").substring(11, 16)

                        val data = SheduleModel(
                            lesson.getInt("LessonId"),
                            lessonInfo.getInt("AuditionNum"),
                            lessonInfo.getString("LessName"),
                            teacherFullName,
                            startTime,
                            endTime,
                            lessonInfo.getString("WeekDateNumbers"))
                        data.cours = group.getInt("Cours")
                        data.faculty = group.getString("Faculty")
                        data.profile = group.getString("TeachProfile")
                        GlobalScope.launch(Dispatchers.Main) {
                            sheduleList[lessonInfo.getInt("DayWeek")].add(data)
                        }
                    }
                }
                GlobalScope.launch(Dispatchers.Main) {
                    retry.visibility = View.INVISIBLE
                    progressBar.visibility = View.INVISIBLE
                    setTodaySheduleList(view)
                }
            } catch (ex: IOException){
                retry.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }
    }
}