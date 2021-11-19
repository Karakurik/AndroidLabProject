package com.itis.androidlabproject.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.itis.androidlabproject.model.Planet

class PlanetDiffItemCallback : DiffUtil.ItemCallback<Planet>() {
    override fun areItemsTheSame(
        oldItem: Planet,
        newItem: Planet
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Planet,
        newItem: Planet
    ): Boolean = oldItem == newItem

    override fun getChangePayload(oldItem: Planet, newItem: Planet): Any? {
        var bundle = Bundle()

        if (oldItem.name != newItem.name) {
            bundle.putString("NAME", newItem.name)
        }
        if (oldItem.numberOfSatellite != newItem.numberOfSatellite) {
            bundle.putInt("NUMBER_OF_SATELLITE", newItem.numberOfSatellite)
        }
        if (oldItem.description != newItem.description) {
            bundle.putString("DESCRIPTION", newItem.description)
        }
        if (oldItem.url != newItem.url) {
            bundle.putString("URL", newItem.url)
        }
        if (bundle.isEmpty) return null
        return bundle
    }
}
