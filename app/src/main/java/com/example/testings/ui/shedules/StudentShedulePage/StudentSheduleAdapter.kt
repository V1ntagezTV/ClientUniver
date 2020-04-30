package com.example.testings.ui.shedules.StudentShedulePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.example.testings.ui.shedules.GroupModel
import com.example.testings.ui.shedules.SheduleModel

class StudentSheduleAdapter: RecyclerView.Adapter<StudentSheduleAdapter.SheduleHolder>(){

    val list: ArrayList<GroupModel> = ArrayList()

    class SheduleHolder(row: View): RecyclerView.ViewHolder(row){
        var Name: TextView = row.findViewById(R.id.shedule_group_name)
        var Cours: TextView = row.findViewById(R.id.shedule_group_course)
        var Faculty: TextView = row.findViewById(R.id.shedule_group_faculty)
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
        holder.Name.text = itemData.Name
        holder.Cours.text = itemData.Cours.toString()
        holder.Faculty.text = itemData.Faculty
    }
}