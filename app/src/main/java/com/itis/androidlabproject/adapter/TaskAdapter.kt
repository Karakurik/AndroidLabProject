package com.itis.androidlabproject.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidlabproject.callbacks.TaskDiffItemCallback
import com.itis.androidlabproject.models.Task
import java.util.ArrayList

class TaskAdapter(
    private val actionChoose: (Int) -> (Unit),
    private val actionDelete: (Int) -> (Unit)
) : ListAdapter<Task, TaskHolder>(TaskDiffItemCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskHolder = TaskHolder.create(parent, actionChoose, actionDelete)

    override fun onBindViewHolder(
        holder: TaskHolder,
        position: Int) = holder.bind(getItem(position))

    override fun onBindViewHolder(
        holder: TaskHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.last().takeIf {
                it is Bundle
            }?.let {
                holder.updateFields(it as Bundle)
            }
        }
    }

    override fun submitList(list: MutableList<Task>?) {
        super.submitList(
            if (list == null) null
            else ArrayList(list)
        )
    }
}
