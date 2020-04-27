package com.example.testings.ui.faculties

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.example.testings.ui.events.EventDetails.EventDetailsActivity
import com.example.testings.ui.events.EventsAdapter
import com.example.testings.ui.faculties.FacultyDetails.FacultyDetails
import com.example.testings.ui.faculties.FacultyDetails.FacultyDetailsModel

class FacultiesAdapter(): RecyclerView.Adapter<FacultiesAdapter.FacultyHolder>(){

    var list: ArrayList<FacultyModel> = ArrayList()

    class FacultyHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var Title: TextView = itemView.findViewById(R.id.faculty_title)
        var Image: ImageView = itemView.findViewById(R.id.faculty_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_faculty_item_content, parent, false)
        return FacultyHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: FacultyHolder, position: Int) {
        val data = list[position]
        holder.Title.text = data.Name
        holder.Image.setImageResource(data.Imageid)
        holder.itemView.setOnClickListener {
            FacultyDetailsModel.id = position
            FacultyDetailsModel.currentModel = data
            val intent = Intent(it.context, FacultyDetails::class.java)
            startActivity(it.context, intent, Bundle())
        }
    }

    fun Set(arrayList: ArrayList<FacultyModel>){
        list.clear()
        list.addAll(arrayList)
        notifyDataSetChanged()
    }
}