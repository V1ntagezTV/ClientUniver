package com.example.testings.ui.events

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R

class EventsAdapter(val navController: NavController): RecyclerView.Adapter<EventsAdapter.EventHolder>(){

    val list: ArrayList<EventModel> = ArrayList()

    class EventHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var Title: TextView = itemView.findViewById(R.id.event_title)
        var PostDate: TextView = itemView.findViewById(R.id.event_date)
        var SmallDescription: TextView = itemView.findViewById(R.id.event_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_events_item_content, parent, false)
        return EventHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val eventData = list[position]
        holder.Title.text = eventData.Title
        holder.PostDate.text = eventData.PostDate
        holder.SmallDescription.text = eventData.SmallDescription

        if ("sibsu.ru/novosti" in eventData.EventPageLink || "sibsu.ru/objavlenija" in eventData.EventPageLink){
            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("link", eventData.EventPageLink)
                navController.navigate(R.id.nav_event_details, bundle)
            }
        } else {
            holder.itemView.findViewById<TextView>(R.id.event__linkTextView).visibility = View.VISIBLE
            holder.itemView.setOnClickListener { v: View ->
                val urls = Uri.parse(eventData.EventPageLink)
                val intent = Intent(Intent.ACTION_VIEW, urls)
                startActivity(v.context, intent, Bundle())
            }
        }
    }

    fun addList(arrayList: ArrayList<EventModel>){
        list.clear()
        list.addAll(arrayList)
        notifyDataSetChanged()
    }

    fun cleanList(){
        list.clear()
        notifyItemRangeRemoved(0, this.itemCount)
    }
}