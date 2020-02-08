package com.example.testings.ui.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R

class EventsAdapter: RecyclerView.Adapter<EventsAdapter.EventHolder>(){

    val list: ArrayList<EventModel> = ArrayList()

    class EventHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var Title: TextView = itemView.findViewById(R.id.event_title)
        var PostDate: TextView = itemView.findViewById(R.id.event_date)
        var SmallDescription: TextView = itemView.findViewById(R.id.event_content)
        var PageLink = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_events_item_content, parent, false)
        return EventHolder(itemView)
    }

    override fun getItemCount(): Int{
        return list.size
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val dataR = list[position]
        holder.Title.text = dataR.Title
        holder.PostDate.text = dataR.PostDate
        holder.SmallDescription.text = dataR.SmallDescription
        holder.PageLink = dataR.EventPageLink
    }

    fun Set(arrayList: ArrayList<EventModel>){
        list.clear()
        list.addAll(arrayList)
        notifyDataSetChanged()
    }
}