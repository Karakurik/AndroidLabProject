package com.itis.androidlabproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidlabproject.databinding.ItemPlanetBinding
import com.itis.androidlabproject.models.Planet

class PlanetHolder(
    private val binding: ItemPlanetBinding,
    private val action: (Int) -> Unit,
    private val glige: RequestManager
) : RecyclerView.ViewHolder(binding.root) {
    private var planet: Planet? = null

    init {
        itemView.setOnClickListener {
            planet?.id?.also(action)
        }
    }

    fun bind(item: Planet) {
        this.planet = item
        with(binding) {
            tvName.text = item.name
            tvAge.text = item.age.toString()
            tvDescription.text = item.description
            glige.load(item.imageUrl).into(ivImage)
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
            action,
            glige
        )
    }
}
