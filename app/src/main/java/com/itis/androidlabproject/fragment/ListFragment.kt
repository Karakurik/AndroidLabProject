package com.itis.androidlabproject.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidsemthree.dialogs.AddDialog
import com.google.android.material.snackbar.Snackbar
import com.itis.androidlabproject.R
import com.itis.androidlabproject.adapter.PlanetListAdapter
import com.itis.androidlabproject.adapter.SwipeToDelete
import com.itis.androidlabproject.databinding.FragmentListBinding
import com.itis.androidlabproject.decorator.SpaceItemDecorator
import com.itis.androidlabproject.repository.PlanetRepository

class ListFragment : Fragment(R.layout.fragment_list) {
    private var binding: FragmentListBinding? = null
    private var planetListAdapter: PlanetListAdapter? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        planetListAdapter = PlanetListAdapter(Glide.with(this)) {
//            showPlanetDetails(it)
            PlanetRepository.delete(it)
            refresh()
        }

        val decorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val spacing = SpaceItemDecorator(requireContext(), 24f)
        binding?.run {
            rvPlanets.run {
                adapter = planetListAdapter
                addItemDecoration(decorator)
                addItemDecoration(spacing)
                swipeToDelete(this)
            }
            refresh()
            btnAdd.setOnClickListener {
                showDialog()
            }

        }

        planetListAdapter?.submitList(PlanetRepository.planets)
    }

    private fun refresh() {
        planetListAdapter?.submitList(PlanetRepository.planets) {
            binding?.rvPlanets?.scrollToPosition(0);
        }
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

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = planetListAdapter?.currentList?.get(viewHolder.absoluteAdapterPosition)
                PlanetRepository.delete(item)
                refresh()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun showDialog() {
        AddDialog.show(
            childFragmentManager,
            positive = { positiveAddDialog(it) },
            notFull = { notFullAlert(it) })
    }

    private fun positiveAddDialog(array: Array<String>) {
        val name = array[0]
        val numberOfSatellite = Integer.parseInt(array[1])
        val desc = array[2]
        val pos: Int? = if (array[3].isEmpty()) {
            null
        } else {
            array[3].toInt()
        }
        PlanetRepository.add(pos, name, numberOfSatellite, desc)
        refresh()
    }

    private fun notFullAlert(flag: Boolean) {
        if (!flag) {
            binding?.root?.let {
                Snackbar.make(
                    it,
                    "Please, enter all data and try again",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
