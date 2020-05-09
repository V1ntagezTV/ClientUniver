package com.example.testings.ui.unstudents


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testings.R
import com.example.testings.ui.unstudents.ProfileDetails.ProfileInfoViewModel

class EducAdapter(var navController: NavController): RecyclerView.Adapter<EducAdapter.EducHolder>(){

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
            ProfileInfoViewModel.profile = EducDB
            navController.navigate(R.id.news_details_Fragment)
        }
    }

    fun Set(list: ArrayList<EducProfileModel>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun CleanList(){
        data.clear()
        notifyItemRangeRemoved(0, this.itemCount)
    }
}