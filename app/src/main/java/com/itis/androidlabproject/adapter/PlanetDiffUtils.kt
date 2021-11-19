package com.itis.androidlabproject.adapter

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import com.itis.androidlabproject.model.Planet

class PlanetDiffUtils(
    private var oldList: List<Planet>,
    private var newList: List<Planet>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
    ): Boolean = oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        var oldPlanet = oldList[oldItemPosition]
        var newPlanet = newList[newItemPosition]

        var bundle = Bundle()

        if (oldPlanet.name != newPlanet.name) {
            bundle.putString("NAME", newPlanet.name)
        }
        if (oldPlanet.numberOfSatellite != newPlanet.numberOfSatellite) {
            bundle.putInt("NUMBER_OF_SATELLITE", newPlanet.numberOfSatellite)
        }
        if (oldPlanet.description != newPlanet.description) {
            bundle.putString("DESCRIPTION", newPlanet.description)
        }
        if (oldPlanet.url != newPlanet.url) {
            bundle.putString("URL", newPlanet.url)
        }
        if (bundle.isEmpty) return null

        return bundle
    }
}
