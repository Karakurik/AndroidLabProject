package com.itis.androidlabproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.itis.androidlabproject.R
import com.itis.androidlabproject.adapter.PlanetAdapter
import com.itis.androidlabproject.databinding.FragmentListBinding
import com.itis.androidlabproject.repository.PlanetRepository

class ListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: PlanetAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        adapter = PlanetAdapter(PlanetRepository.planets, Glide.with(this)) {
            showPlanetDetails(it);
        }
        binding.rvPlanets.adapter = adapter

        return binding.root
    }

    private fun showPlanetDetails(id: Int) {
        val bundle = bundleOf(
            "planetId" to id
        )

        val options = NavOptions.Builder()
            .setLaunchSingleTop(false)
            .setEnterAnim(R.anim.enter_from_right)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.exit_to_right)
            .build()
        findNavController().navigate(R.id.action_listFragment_to_detailsFragment, bundle, options)
    }
}
