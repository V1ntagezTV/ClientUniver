package com.example.testings.ui.unstudents


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R

class EducAdapter(var navController: NavController): RecyclerView.Adapter<EducAdapter.EducHolder>(){

    var data: ArrayList<ProfileItemModel> = ArrayList()

    class EducHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.unstud_it_title)
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
        val element = data[position]
        holder.title.text = element.title

        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("link", element.link)
            navController.navigate(R.id.action_nav_unstudents_to_nav_profile_info, bundle)
        }
    }

    fun setList(list: ArrayList<ProfileItemModel>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun cleanList(){
        data.clear()
        notifyItemRangeRemoved(0, this.itemCount)
    }
}