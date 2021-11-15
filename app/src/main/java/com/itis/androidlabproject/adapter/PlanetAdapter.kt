package com.itis.androidlabproject.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidlabproject.model.Planet

class PlanetAdapter(
    private val list: List<Planet>,
    private val glide: RequestManager,
    private val action: (Int) -> Unit
) : RecyclerView.Adapter<PlanetHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlanetHolder = PlanetHolder.create(parent, glide, action)

    override fun onBindViewHolder(holder: PlanetHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size;

}
