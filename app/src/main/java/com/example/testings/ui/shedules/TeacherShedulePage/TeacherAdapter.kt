package com.example.testings.ui.shedules.TeacherShedulePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R

class TeacherAdapter: RecyclerView.Adapter<TeacherAdapter.TeacherHolder>() {

    var list: ArrayList<TeacherModel> = ArrayList()

    class TeacherHolder(view: View): RecyclerView.ViewHolder(view) {
        val FSName = view.findViewById<TextView>(R.id.teacher_FirstSec_Name)
        val MiddleName = view.findViewById<TextView>(R.id.teacher_MiddleName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_shedule_teacher_item_content, parent, false)
        return TeacherHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TeacherHolder, position: Int) {
        val value = list[position]
        holder.FSName.text = value.LastName + " " +  value.FirstName
        holder.MiddleName.text = value.MiddleName
    }


}