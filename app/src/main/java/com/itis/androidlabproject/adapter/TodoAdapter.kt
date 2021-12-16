package com.itis.androidlabproject.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidlabproject.models.Todo

class TodoAdapter(
    private val itemList: List<Todo>,
    private val itemClick: (Int) -> (Unit)
) : RecyclerView.Adapter<TodoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        return TodoHolder.create(parent, itemClick)
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}
