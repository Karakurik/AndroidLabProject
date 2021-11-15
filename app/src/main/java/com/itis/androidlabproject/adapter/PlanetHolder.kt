package com.itis.androidlabproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itis.androidlabproject.databinding.ItemPlanetBinding
import com.itis.androidlabproject.model.Planet

class PlanetHolder(
    private val binding: ItemPlanetBinding,
    private val glige: RequestManager,
    private val action: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var planet: Planet? = null

    private val options = RequestOptions()
        .priority(Priority.HIGH)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    init {
        itemView.setOnClickListener {
            planet?.run {
                action(this.id)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Planet) {
        this.planet = item
        with(binding) {
            tvDetName.text = item.name
            tvNumberOfSatellite.text = "Количество спутников: ${item.numberOfSatellite}"

            glige.load(item.url)
                .apply(options)
                .into(ivImage)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            glige: RequestManager,
            action: (Int) -> Unit
        ) = PlanetHolder(
            ItemPlanetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glige,
            action
        )
    }
}
