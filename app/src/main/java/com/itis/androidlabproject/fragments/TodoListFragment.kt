package com.itis.androidlabproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.androidlabproject.R
import com.itis.androidlabproject.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment() {
    private var binding: FragmentTodoListBinding? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodoListBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
