package com.example.interview_music.data.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.interview_music.R



class PlaybackService : MediaSessionService() {
    private var mediaSession: MediaSession? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()  // Step 1
        startForeground(NOTIFICATION_ID, createNotification())
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()  // Step 1
        startForeground(NOTIFICATION_ID, createNotification())
          // Then initialize player/media session
        val player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()


    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "music_channel_id",  // Same ID used in NotificationCompat.Builder
                "Music Player",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Music playback controls"
            }
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }
    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "music_channel_id")  // Must match channel ID
            .setContentTitle("Music Player")
            .setContentText("Now Playing")
            .setSmallIcon(R.drawable.ic_launcher_foreground)  // MUST be a valid white-on-transparent icon
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    companion object {
        const val NOTIFICATION_ID = 123
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
       return mediaSession
    }

    // Remember to release the player and media session in onDestroy
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}