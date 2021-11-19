package com.itis.androidlabproject.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.itis.androidlabproject.model.Planet
import java.util.ArrayList

class PlanetListAdapter(
    private val glide: RequestManager,
    private val action: (Int) -> Unit
) : ListAdapter<Planet, PlanetHolder>(PlanetDiffItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlanetHolder = PlanetHolder.create(parent, glide, action)

    override fun onBindViewHolder(
        holder: PlanetHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: PlanetHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.last().takeIf { it is Bundle }?.let {
                holder.updateFields(it as Bundle)
            }
        }
    }

    override fun submitList(list: MutableList<Planet>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
