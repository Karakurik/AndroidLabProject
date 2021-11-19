package com.itis.androidlabproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itis.androidlabproject.R
import com.itis.androidlabproject.adapter.PlanetCardViewAdapter
import com.itis.androidlabproject.databinding.FragmentCardBinding
import com.itis.androidlabproject.decorator.SpaceItemDecorator
import com.itis.androidlabproject.repository.PlanetRepository

class CardViewFragment : Fragment() {
    private lateinit var binding: FragmentCardBinding
    private lateinit var albumCardViewAdapter: PlanetCardViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumCardViewAdapter = PlanetCardViewAdapter(PlanetRepository.planets, Glide.with(this))

        val decorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val spacing = SpaceItemDecorator(requireContext())

        with(binding) {
            rvPlanets.run {
                adapter = albumCardViewAdapter
                addItemDecoration(decorator)
                addItemDecoration(spacing)
            }
        }
    }
}
