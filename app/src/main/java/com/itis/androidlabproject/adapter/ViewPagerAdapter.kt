package com.itis.androidlabproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidlabproject.databinding.ItemViewpagerBinding


class ViewPagerAdapter(
    private val url: String,
    private val glide: RequestManager,
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {


    inner class ViewPagerViewHolder(
        private val binding: ItemViewpagerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            with(binding) {
                glide.load(url).into(ivImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(
            ItemViewpagerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = 1
}
