package com.itis.androidlabproject.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidlabproject.databinding.TaskListItemBinding
import com.itis.androidlabproject.models.DateToStringConverter.convertDateToString
import com.itis.androidlabproject.models.Task

class TaskHolder(
    private val binding: TaskListItemBinding,
    private val actionChoose: (Int) -> (Unit),
    private val actionDelete: (Int) -> (Unit)
) : RecyclerView.ViewHolder(binding.root) {
    private var task: Task? = null

    fun bind(item: Task) {
        this.task = item
        with(binding) {
            tvTaskTitle.text = item.title
            tvTaskDate.text = convertDateToString(item.date)

            itemView.setOnClickListener {
                actionChoose(item.id)
            }
            ivDelete.setOnClickListener {
                actionDelete(item.id)
            }
        }
    }

    fun updateFields(bundle: Bundle?) {
        bundle?.run {
            getString("TITLE")?.also {
                updateTitle(it)
            }
            getString("DATE")?.also {
                updateDate(it)
            }
        }
    }

    private fun updateTitle(title: String) {
        binding.tvTaskTitle.text = title
    }

    private fun updateDate(date: String) {
        binding.tvTaskDate.text = date
    }

    companion object {
        fun create(
            parent: ViewGroup,
            actionChoose: (Int) -> Unit,
            actionDelete: (Int) -> Unit
        ) = TaskHolder(
            TaskListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            actionChoose,
            actionDelete
        )
    }
}
