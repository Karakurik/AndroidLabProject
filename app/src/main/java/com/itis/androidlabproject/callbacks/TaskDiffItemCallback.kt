package com.itis.androidlabproject.callbacks

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import com.itis.androidlabproject.models.DateToStringConverter
import com.itis.androidlabproject.models.Task

class TaskDiffItemCallback : DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(
        oldItem: Task,
        newItem: Task
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Task,
        newItem: Task
    ): Boolean {
        return oldItem.equals(newItem)
    }

    override fun getChangePayload(
        oldItem: Task,
        newItem: Task
    ): Any? {
        val bundle = Bundle().apply {
            if (oldItem.title != newItem.title) {
                putString("TITLE", newItem.title)
            }

            if (oldItem.description != newItem.description) {
                putString("DESCRIPTION", newItem.description)
            }

            if (oldItem.date != newItem.date) {
                putString("DATE", DateToStringConverter.convertDateToString(newItem.date))
            }

            if (oldItem.latitude != newItem.latitude) {
                newItem.latitude?.let {
                    putDouble("LATITUDE", it)
                }
            }

            if (oldItem.longitude != newItem.longitude) {
                newItem.longitude?.let {
                    putDouble("LONGITUDE", it)
                }
            }
        }


        if (bundle.isEmpty) return null
        return bundle
    }
}
