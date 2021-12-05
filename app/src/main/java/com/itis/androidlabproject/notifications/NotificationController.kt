package com.itis.androidlabproject.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import com.itis.androidlabproject.R
import com.itis.androidlabproject.fragments.TrackDetailsFragment
import com.itis.androidlabproject.repository.TrackRepository
import com.itis.androidlabproject.services.MusicService
import com.itis.androidlabproject.view.MainActivity


class NotificationController(
    private val context: Context
) {

    private val CHANNEL_ID = "music_channel"
    private val notificationId = 1

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    private var nBuilder: NotificationCompat.Builder

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Player"
            val descriptionText = "Notificaton Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                notificationManager.getNotificationChannel(CHANNEL_ID) ?: NotificationChannel(
                    CHANNEL_ID,
                    name,
                    importance
                ).apply {
                    description = descriptionText
                }
            notificationManager.createNotificationChannel(channel)
        }

        val previousIntent = Intent(context, MusicService::class.java).apply {
            action = "PREVIOUS"
        }
        val resumeIntent = Intent(context, MusicService::class.java).apply {
            action = "RESUME"
        }
        val nextIntent = Intent(context, MusicService::class.java).apply {
            action = "NEXT"
        }
        val previousPendingIntent = PendingIntent.getService(context, 0, previousIntent, 0)
        val resumePendingIntent = PendingIntent.getService(context, 1, resumeIntent, 0)
        val nextPendingIntent = PendingIntent.getService(context, 2, nextIntent, 0)

        nBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.play_arrow_24px)
            .addAction(R.drawable.skip_previous_24px, "Previous", previousPendingIntent)
            .addAction(R.drawable.games_24px, "Pause/Play", resumePendingIntent)
            .addAction(R.drawable.skip_next_24px, "Next", nextPendingIntent)
    }


    fun build(trackId: Int) {
        val track = TrackRepository.tracksList[trackId]
        val cover = BitmapFactory.decodeResource(context.resources, track.cover)

        val contentIntent = Intent(context, MainActivity::class.java)
            .putExtras(bundleOf(
                "id" to trackId
            )).let {
                PendingIntent.getActivities(
                    context,
                    0,
                    arrayOf(it),
                    PendingIntent.FLAG_ONE_SHOT
                )
            }

        val builder = nBuilder
            .setContentTitle(track.title)
            .setContentText(track.author)
            .setLargeIcon(cover)
            .setContentIntent(contentIntent)

        val notification: Notification = builder.build()
        notificationManager.notify(notificationId, notification)
    }
}
