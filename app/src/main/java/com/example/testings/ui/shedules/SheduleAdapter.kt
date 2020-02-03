package com.example.testings.ui.shedules

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R

class SheduleAdapter(val list: ArrayList<SheduleModel>): RecyclerView.Adapter<SheduleAdapter.SheduleHolder>(){

    class SheduleHolder(row: View): RecyclerView.ViewHolder(row){
        var Title: TextView = row.findViewById(R.id.shedule_less_name)
        var Cab: TextView = row.findViewById(R.id.shedule_less_cab)
        var Teacher: TextView = row.findViewById(R.id.shedule_less_teacher)
        var DateTimeStart: TextView = row.findViewById(R.id.shedule_less_start)
        var DateTimeEnd: TextView = row.findViewById(R.id.shedule_less_end)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SheduleHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_shedule_item_content, parent, false)
        return SheduleHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: SheduleHolder, position: Int) {
        val itemData = list[position]
        holder.Title.text = itemData.Title
        holder.Teacher.text = itemData.Teacher
        holder.Cab.text = itemData.Cab.toString()
        holder.DateTimeStart.text = itemData.DateTimeStart.value.Start
        holder.DateTimeEnd.text = itemData.DateTimeStart.value.End
    }
}