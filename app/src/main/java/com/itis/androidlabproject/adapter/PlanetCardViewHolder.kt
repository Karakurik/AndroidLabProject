package com.itis.androidlabproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itis.androidlabproject.databinding.ItemCvPlanetBinding
import com.itis.androidlabproject.model.Planet

class PlanetCardViewHolder(
    private val binding: ItemCvPlanetBinding,
    private val glide: RequestManager,
) : RecyclerView.ViewHolder(binding.root) {

    private var album: Planet? = null

    private val options = RequestOptions()
        .priority(Priority.HIGH)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    fun bind(item: Planet) {
        this.album = item
        with(binding) {
            tvName1.text = item.name
            tvName2.text = item.name
            tvNumberOfSatellite.text = "Количество спутников: ${item.numberOfSatellite}"
            tvDesc.text = item.description

            glide.load(item.url)
                .apply(options)
                .into(ivImage1)
            vp2Images.adapter = ViewPagerAdapter(item.url, glide)
        }

    }

    companion object {
        fun create(
            parent: ViewGroup,
            glide: RequestManager,
        ) = PlanetCardViewHolder(
            ItemCvPlanetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), glide
        )
    }
}
