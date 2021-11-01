package com.itis.androidlabproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.itis.androidlabproject.R
import com.itis.androidlabproject.adapters.PlanetAdapter
import com.itis.androidlabproject.databinding.FragmentListBinding
import com.itis.androidlabproject.repositories.PlanetRepository

class ListFragment: Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: PlanetAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        adapter = PlanetAdapter(PlanetRepository.planets, Glide.with(this)) {
            showDetails(it);
        }
        binding.rvPlanets.adapter = adapter

        return binding.root
    }

    private fun showDetails(id: Int) {
        val bundle = Bundle()
        bundle.putInt("id", id)
        findNavController().navigate(R.id.action_listFragment_to_detailsFragment)
    }


    private fun showName(name: String) {
        Snackbar.make(
            binding.root,
            "Name: $name",
            Snackbar.LENGTH_LONG
        ).show()
    }
}
