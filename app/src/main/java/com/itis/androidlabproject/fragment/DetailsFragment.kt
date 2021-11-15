package com.itis.androidlabproject.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.itis.androidlabproject.databinding.FragmentDetailsBinding
import com.itis.androidlabproject.model.Planet
import com.itis.androidlabproject.repository.PlanetRepository

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            arguments?.getInt("planetId")?.let { planetId ->
                PlanetRepository.getPlanetById(planetId).let {
                    showChosenPlanet(it)
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showChosenPlanet(planet: Planet) {
        with(binding) {
            Glide.with(this.root).load(planet.url).into(ivPhoto)
            tvDetName.text = "Название планеты: ${planet.name}"
            tvDetNumberOfSatellite.text = "Количество спутников ${planet.numberOfSatellite}"
            tvDetDescription.text = "Описание: ${planet.description}"
            collapsingToolbar.title = planet.name
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
