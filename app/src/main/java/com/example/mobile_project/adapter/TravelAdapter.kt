package com.example.mobile_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.R
import com.example.mobile_project.model.TravelRecord
import android.widget.Button
class TravelAdapter(
    private val list: ArrayList<TravelRecord>,
    private val onItemClick:(TravelRecord)->Unit,
    private val onDelete:(TravelRecord)->Unit
) : RecyclerView.Adapter<TravelAdapter.ViewHolder>()
{
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val tvPlace: TextView = view.findViewById(R.id.tvPlace)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val record = list[position]
        holder.tvPlace.text = record.place
        holder.tvDate.text = record.date
        holder.btnEdit.setOnClickListener {
            onItemClick(record)
        }
        holder.btnDelete.setOnClickListener {
            onDelete(record)
        }
    }

    override fun getItemCount(): Int
    {
        return list.size
    }
}