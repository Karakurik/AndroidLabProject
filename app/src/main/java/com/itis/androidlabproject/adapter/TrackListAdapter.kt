package com.itis.androidlabproject.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidlabproject.models.Track

class TrackListAdapter(
    private val trackList: ArrayList<Track>,
    private val itemClick: (Int) -> (Unit)

) : RecyclerView.Adapter<TrackHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        return TrackHolder.create(parent, itemClick)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount() = trackList.size
}
