package com.itis.androidlabproject.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidlabproject.R
import com.itis.androidlabproject.adapter.TrackListAdapter
import com.itis.androidlabproject.databinding.FragmentTrackListBinding
import com.itis.androidlabproject.decorator.SpaceItemDecorator
import com.itis.androidlabproject.repository.TrackRepository

class TrackListFragment : Fragment(R.layout.fragment_track_list) {
    private var binding: FragmentTrackListBinding? = null
    private var trackListAdapter: TrackListAdapter? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrackListBinding.bind(view)

        trackListAdapter = TrackListAdapter(TrackRepository.tracksList) { id ->
            var args = bundleOf(
                "trackId" to id
            )

            findNavController().navigate(R.id.action_trackListFragment_to_trackDetailsFragment, args)
        }

        val decorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val spacing = SpaceItemDecorator(requireContext(), 16f)
        binding?.run {
            rvTracks.run {
                adapter = trackListAdapter
                addItemDecoration(decorator)
                addItemDecoration(spacing)
            }
        }

        activity?.intent?.extras?.getInt("id")?.let { id ->
            var args = bundleOf(
                "trackId" to id
            )
            findNavController().navigate(R.id.action_trackListFragment_to_trackDetailsFragment, args)
        }
        activity?.intent?.removeExtra("id")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        trackListAdapter = null
    }

}
