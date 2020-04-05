package com.example.testings.ui.unstudents

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.example.testings.ui.unstudents.ProfileDetails.ProfileInfoActivity
import com.example.testings.ui.unstudents.ProfileDetails.ProfileInfoViewModel

class EducAdapter: RecyclerView.Adapter<EducAdapter.EducHolder>(){

    var data: ArrayList<EducProfileModel> = ArrayList()

    class EducHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var codeV = itemView.findViewById<TextView>(R.id.unstud_row_code)
        var nameV = itemView.findViewById<TextView>(R.id.unstud_row_name)
        var facultyV = itemView.findViewById<TextView>(R.id.unstud_row_faculty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_unstudent_item_content, parent, false)
        return EducHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: EducHolder, position: Int) {
        val EducDB = data[position]
        holder.codeV.text = EducDB.Code
        holder.facultyV.text = EducDB.Faculty
        holder.nameV.text = EducDB.Name

        holder.itemView.setOnClickListener{
            val intent = Intent(it.context, ProfileInfoActivity::class.java)
            ProfileInfoViewModel.profile = EducDB
            startActivity(it.context, intent, Bundle())
        }
    }

    fun Set(list: ArrayList<EducProfileModel>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
}