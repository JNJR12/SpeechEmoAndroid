package com.example.speechemo

import android.app.Activity
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UploadAudioActivity : AppCompatActivity() {
    private lateinit var audioPlayer: AudioPlayer
    private var audioUri: Uri? = null
    private lateinit var playbackStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_audio)

        audioPlayer = AudioPlayer()
        val btnUpload: Button = findViewById(R.id.btnUpload)
        val btnPlayUploaded: Button = findViewById(R.id.btnPlayUploaded)
        playbackStatus = findViewById(R.id.playbackStatus)

        btnUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "audio/*"
            }
            startActivityForResult(intent, REQUEST_AUDIO_FILE)
        }

        btnPlayUploaded.setOnClickListener {
            audioUri?.let { uri ->
                if (!audioPlayer.isPlaying) {
                    audioPlayer.startPlaying(uri, applicationContext) { playing ->
                        btnPlayUploaded.text = if (playing) "Stop Playing" else "Play Audio"
                    }
                    btnPlayUploaded.text = getString(R.string.stop_playing)
                } else {
                    audioPlayer.stopPlaying()
                    btnPlayUploaded.text = getString(R.string.play_audio)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_AUDIO_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri ->
                audioUri = uri
                findViewById<Button>(R.id.btnPlayUploaded).isEnabled = true

                playbackStatus.text = getString(R.string.file_ready_for_playback)
                Toast.makeText(this, "File successfully uploaded", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_AUDIO_FILE = 1
    }
}
