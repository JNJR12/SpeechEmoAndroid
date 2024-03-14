package com.example.speechemo

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import java.io.IOException

class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun startPlaying(audioUri: Uri, context: Context, onPlaybackStatusChanged: (Boolean) -> Unit) {
        stopPlaying()
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            try {
                setDataSource(context, audioUri)
                prepare()
                start()
                onPlaybackStatusChanged(true)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            setOnCompletionListener {
                onPlaybackStatusChanged(false)
            }
        }
    }

    fun stopPlaying() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
        }
        mediaPlayer?.release()
        mediaPlayer = null
    }

    val isPlaying: Boolean
        get() = mediaPlayer?.isPlaying ?: false
}
