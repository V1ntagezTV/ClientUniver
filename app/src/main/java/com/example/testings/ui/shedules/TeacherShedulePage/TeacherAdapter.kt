package com.example.testings.ui.shedules.TeacherShedulePage

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TeacherAdapter: RecyclerView.Adapter<TeacherAdapter.TeacherHolder>() {

    var list: ArrayList<TeacherModel> = ArrayList()

    class TeacherHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TeacherHolder, position: Int) {
        TODO("Not yet implemented")
    }
}