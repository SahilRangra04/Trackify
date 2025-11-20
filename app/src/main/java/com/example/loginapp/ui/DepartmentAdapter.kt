package com.example.loginapp.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.model.Department
import com.google.android.material.card.MaterialCardView

class DepartmentAdapter(
    private val departments: List<Department>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_department, parent, false)
        return DepartmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        val department = departments[position]
        holder.bind(department)
        holder.itemView.setOnClickListener { onClick(department.id) }
    }

    override fun getItemCount(): Int = departments.size

    class DepartmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.ivIcon)
        private val name: TextView = itemView.findViewById(R.id.tvName)
        private val card: MaterialCardView? = itemView as? MaterialCardView

        fun bind(dep: Department) {
            name.text = dep.name
            icon.setImageResource(dep.iconResId)
            // card background color based on provided colorHex
            try {
                val color = Color.parseColor(dep.colorHex)
                card?.setCardBackgroundColor(color)
            } catch (_: Throwable) {
                // ignore parse error; keep default bg color
            }

            // Make only CS & IT department's icon behave as a button and slightly smaller
            val density = itemView.resources.displayMetrics.density
            if (dep.id == 1) {
                // 56dp for CS & IT
                val sizePx = (56f * density).toInt()
                val lp = icon.layoutParams
                lp.width = sizePx
                lp.height = sizePx
                icon.layoutParams = lp
                icon.isClickable = true
                icon.setOnClickListener { itemView.callOnClick() }
            } else {
                // 72dp for other departments
                val sizePx = (72f * density).toInt()
                val lp = icon.layoutParams
                lp.width = sizePx
                lp.height = sizePx
                icon.layoutParams = lp
                icon.isClickable = false
                icon.setOnClickListener(null)
            }
        }
    }
}


