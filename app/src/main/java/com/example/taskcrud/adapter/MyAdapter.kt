package com.example.taskcrud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import com.example.taskcrud.R
import com.example.taskcrud.classes.TaskData
import com.example.taskcrud.database.DatabaseHelper

class MyAdapter(private val dataList: List<TaskData>, private val context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTaskTextView: TextView = itemView.findViewById(R.id.nameTaskTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.nameTaskTextView.text = currentItem.nameTask
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun removeItem(position: Int) {
        val deletedItem = TaskData.removeAt(position)
        dbHelper.deleteTask(deletedItem.id)
        notifyItemRemoved(position)
    }
}
