package com.example.testings.ui.shedules.ShedulePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R

class SheduleAdapter: RecyclerView.Adapter<SheduleAdapter.SheduleHolder>() {

    var list: ArrayList<SheduleModel> = ArrayList()

    class SheduleHolder(view: View): RecyclerView.ViewHolder(view){
        val start = view.findViewById<TextView>(R.id.shedule_it_start)
        val end = view.findViewById<TextView>(R.id.shedule_it_end)
        val bottomInfo = view.findViewById<TextView>(R.id.shedule_it_teacher)
        val title = view.findViewById<TextView>(R.id.shedule_it_title)
        val cab = view.findViewById<TextView>(R.id.shedule_it_cab)
        val weeks = view.findViewById<TextView>(R.id.shedule_it_weeks)
        val num = view.findViewById<TextView>(R.id.shedule_it_num)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SheduleHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_shedule_details_item_content, parent, false)
        return SheduleHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: SheduleHolder, position: Int) {
        val data = list[position]
        var info = data.teacher
        holder.cab.text = data.cab.toString()
        holder.title.text = data.title
        holder.end.text = data.end
        holder.start.text = data.start
        holder.weeks.text = data.weeks
        holder.num.text = (position + 1).toString()
        if (SheduleModelView.currentType == "teacher"){
            info ="" + data.cours + " "+ data.profile
        }
        holder.bottomInfo.text = info
    }

    fun addList(arr: ArrayList<SheduleModel>){
        list.clear()
        list.addAll(arr)
        this.notifyDataSetChanged()
    }

}