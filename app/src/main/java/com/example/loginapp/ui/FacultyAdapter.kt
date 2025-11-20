package com.example.loginapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.model.Faculty

class FacultyAdapter(
    private var faculty: List<Faculty>,
    private val onClick: (Faculty) -> Unit,
    private val departmentId: Int? = null // optional, used for new Activity
) : RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder>() {

    fun updateData(newFaculty: List<Faculty>) {
        faculty = newFaculty
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_faculty, parent, false)
        return FacultyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        val item = faculty[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = faculty.size

    class FacultyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvFacultyName)
        fun bind(item: Faculty) {
            name.text = item.name
        }
    }
}








