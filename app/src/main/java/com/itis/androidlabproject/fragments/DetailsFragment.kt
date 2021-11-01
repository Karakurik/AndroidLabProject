package com.itis.androidlabproject.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.itis.androidlabproject.databinding.FragmentDetailsBinding
import com.itis.androidlabproject.models.Planet
import com.itis.androidlabproject.repositories.PlanetRepository
import java.util.*

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var planet: Optional<Planet>? = null
        arguments?.getInt("id").also { id ->
            planet = PlanetRepository.planets.stream().filter {
                it.id == id
            }.findFirst()
        }
        if (planet?.isPresent == true) {
            binding.tvDetName.text = planet?.get()?.name
            binding.tvDetAge.text = planet?.get()?.age.toString()
            binding.tvDetDescription.text = planet?.get()?.description
        }
    }

}
