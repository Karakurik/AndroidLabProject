package com.itis.androidlabproject.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidlabproject.model.Planet

class PlanetCardViewAdapter(
    private val list: List<Planet>,
    private val glide: RequestManager,
) : RecyclerView.Adapter<PlanetCardViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlanetCardViewHolder = PlanetCardViewHolder.create(parent, glide)

    override fun onBindViewHolder(holder: PlanetCardViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
