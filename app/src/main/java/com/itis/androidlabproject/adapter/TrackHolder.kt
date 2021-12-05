package com.itis.androidlabproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidlabproject.databinding.TrackListItemBinding
import com.itis.androidlabproject.models.Track

class TrackHolder(
    private val binding: TrackListItemBinding,
    private val itemClick: (Int) -> (Unit)
) : RecyclerView.ViewHolder(binding.root) {

    private var trackToDisplay: Track? = null


    init {
        itemView.setOnClickListener {
            trackToDisplay?.also {
                itemClick(it.id)
            }
        }
    }

    fun bind(track: Track) {
        trackToDisplay = track
        with(binding) {
            trackListItemTitle.text = track.title
            trackListItemAuthor.text = track.author
            trackListItemCover.setImageResource(track.cover)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            itemClick: (Int) -> Unit
        ) = TrackHolder(
            TrackListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), itemClick
        )
    }
}
