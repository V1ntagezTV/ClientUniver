package com.example.testings.ui.shedules.StudentShedulePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.example.testings.ui.shedules.ShedulePage.SheduleModelView

class StudentSheduleAdapter(var navController: NavController): RecyclerView.Adapter<StudentSheduleAdapter.SheduleHolder>(){

    val list: ArrayList<GroupModel> = ArrayList()

    class SheduleHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var Name: TextView = itemView.findViewById(R.id.shedule_group_name)
        var Cours: TextView = itemView.findViewById(R.id.shedule_group_course)
        var Faculty: TextView = itemView.findViewById(R.id.shedule_group_faculty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SheduleHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_shedule_student_item_content, parent, false)
        return SheduleHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: SheduleHolder, position: Int) {
        val itemData = list[position]
        holder.Name.text = itemData.Name
        holder.Cours.text = "Курс: " + itemData.Cours.toString()
        holder.Faculty.text = "Факультет: " + itemData.Faculty
        holder.itemView.setOnClickListener { v: View ->
            SheduleModelView.currentId = itemData.Id
            SheduleModelView.currentType = "student"
            navController.navigate(R.id.nav_shedule_details)
        }
    }
}