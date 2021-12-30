package com.itis.androidlabproject.services

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.IBinder
import com.itis.androidlabproject.IMyMusicInterface
import com.itis.androidlabproject.notifications.NotificationController
import com.itis.androidlabproject.repository.TrackRepository

class MusicService : Service() {
    private val CHANNEL_ID = "music_channel"
    private var currentTrackId: Int? = null

    private var mediaPlayer: MediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }

    private var notificationController: NotificationController? = null

    private fun initNotificationController () {
        notificationController = NotificationController(this)
    }

    override fun onCreate() {
        super.onCreate()
        currentTrackId = 0
        initNotificationController()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        when (intent?.action) {
            "PREVIOUS" -> {
                playPreviousTrack()
            }
            "RESUME" -> {
                if (mediaPlayer.isPlaying) pauseTrack() else playTrack()
            }
            "NEXT" -> {
                playNextTrack()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    inner class AidlBinder : IMyMusicInterface.Stub() {
        override fun playPreviousTrack() {
            this@MusicService.playPreviousTrack()
        }

        override fun playNextTrack() {
            this@MusicService.playNextTrack()
        }

        override fun pauseTrack() {
            this@MusicService.pauseTrack()
        }

        override fun playTrack() {
            this@MusicService.playTrack()
        }

        override fun setTrack(id: Int) {
            this@MusicService.setTrack(id)
        }

    }

    private fun playPreviousTrack() {
        currentTrackId?.let { id ->
            currentTrackId = if (id == 0) {
                TrackRepository.tracksList.size - 1
            } else {
                id - 1
            }
        }
        setTrack(currentTrackId?: 0)
        playTrack()
    }

    private fun playNextTrack() {
        currentTrackId?.let { id ->
            currentTrackId = if (id == TrackRepository.tracksList.size - 1) {
                0
            } else {
                id + 1
            }
        }
        setTrack(currentTrackId?: 0)
        playTrack()
    }

    private fun setTrack(id: Int) {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer = MediaPlayer.create(applicationContext, TrackRepository.tracksList[id].sound)
        currentTrackId = id
        mediaPlayer.run {
            setOnCompletionListener {
                stop()
            }
        }
        notificationController?.build(id)
    }

    private fun playTrack() {
        mediaPlayer.start()
    }

    private fun pauseTrack() {
        mediaPlayer.pause();
    }


    override fun onBind(intent: Intent?): IBinder = AidlBinder()

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
