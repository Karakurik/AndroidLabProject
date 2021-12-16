package com.itis.androidlabproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidlabproject.databinding.TodoListItemBinding
import com.itis.androidlabproject.models.Todo

class TodoHolder(
    private val binding: TodoListItemBinding,
    private val itemClick: (Int) -> (Unit)
) : RecyclerView.ViewHolder(binding.root) {
    private var itemToShow: Todo? = null

    fun bind(todo: Todo) {
        itemToShow = todo
        with(binding) {

        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            itemClick: (Int) -> Unit
        ) = TodoHolder(
            TodoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick
        )
    }
}
