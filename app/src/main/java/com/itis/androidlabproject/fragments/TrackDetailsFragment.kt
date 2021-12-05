package com.itis.androidlabproject.fragments

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.itis.androidlabproject.IMyMusicInterface
import com.itis.androidlabproject.R
import com.itis.androidlabproject.databinding.FragmentTrackDetailsBinding
import com.itis.androidlabproject.models.Track
import com.itis.androidlabproject.repository.TrackRepository
import com.itis.androidlabproject.services.MusicService

class TrackDetailsFragment : Fragment(R.layout.fragment_track_details) {
    private var binding: FragmentTrackDetailsBinding? = null

    private var binderAidl: IMyMusicInterface? = null

    private val connectionAidl =object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            binderAidl = IMyMusicInterface.Stub.asInterface(service)
            initView()
        }

        override fun onServiceDisconnected(
            name: ComponentName?
        ) {
            binderAidl = null
        }
    }

    private var currentTrack: Track? = null

    private fun initView() {
        val id = arguments?.getInt("trackId")

        id?.let {
            currentTrack = TrackRepository.tracksList[id]
        }

        binding?.run {
            tvTitle.text = currentTrack?.title
            tvAuthor.text = currentTrack?.author
            currentTrack?.cover?.let { ivCover.setImageResource(it) }
        }

        initMusicNavigationView(id)
    }

    private fun initMusicNavigationView(id: Int?) {
        id?.let {id ->
            binderAidl?.setTrack(id)
            binderAidl?.playTrack()

            binding?.run {
                btnPlay.setOnClickListener {
                    binderAidl?.playTrack()
                    showPauseButton()
                }
                btnPrevious.setOnClickListener {
                    binderAidl?.playPreviousTrack()
                    if (id== 0) {
                        updateView(TrackRepository.tracksList.size-1)
                    } else {
                        updateView(id-1)
                    }
                }
                btnNext.setOnClickListener {
                    binderAidl?.playNextTrack()
                    if (id == TrackRepository.tracksList.size-1) {
                        updateView(0)
                    } else {
                        updateView(id+1)
                    }
                }
                btnPause.setOnClickListener {
                    binderAidl?.pauseTrack()
                    showPlayButton()
                }
            }
        }
    }

    private fun updateView(id:Int){
        id.let {
            currentTrack = TrackRepository.tracksList[id];
        }

        binding?.run {
            tvTitle.text = currentTrack?.title
            tvAuthor.text = currentTrack?.author
            currentTrack?.cover?.let { ivCover.setImageResource(it) }
        }

        showPauseButton()
        initMusicNavigationView(id)
    }


    private fun showPauseButton(){
        binding?.run {
            btnPlay.visibility = View.GONE
            btnPause.visibility = View.VISIBLE
        }
    }

    private fun showPlayButton(){
        binding?.run {
            btnPause.visibility = View.GONE
            btnPlay.visibility = View.VISIBLE
        }
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        binding = FragmentTrackDetailsBinding.inflate(inflater, container, false)
        activity?.bindService(
            Intent(activity, MusicService::class.java),
            connectionAidl,
            AppCompatActivity.BIND_AUTO_CREATE
        )
        return binding?.root
    }
}
