package com.example.testings.ui.shedules.TeacherShedulePage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.example.testings.ui.shedules.SheduleDetailsActivity

class TeacherAdapter(var navController: NavController): RecyclerView.Adapter<TeacherAdapter.TeacherHolder>() {

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

        holder.itemView.setOnClickListener { v: View ->
            val intent = Intent(v.context, SheduleDetailsActivity::class.java)
            startActivity(v.context, intent, Bundle())
        }
    }
}