package com.example.speechemo

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class RecordAudioActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }

    private lateinit var audioRecorder: AudioRecorder
    private lateinit var audioPlayer: AudioPlayer
    private var lastRecordedFilePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_audio)

        audioRecorder = AudioRecorder()
        audioPlayer = AudioPlayer()

        val recordButton: Button = findViewById(R.id.recordButton)
        val playButton: Button = findViewById(R.id.playButton)

        recordButton.setOnClickListener {
            if (!audioRecorder.isRecording) {
                // Request permission before starting recording
                requestAudioPermission()
            } else {
                audioRecorder.stopRecording()
                recordButton.text = getString(R.string.start_recording)
                lastRecordedFilePath = "${externalCacheDir?.absolutePath}/audio_record.m4a"
                recordButton.text = getString(R.string.start_recording)
            }
        }

        playButton.setOnClickListener {
            lastRecordedFilePath?.let { filePath ->
                val file = File(filePath)
                val uri = Uri.parse(filePath)
                if (!audioPlayer.isPlaying) {
                    audioPlayer.startPlaying(uri, this) { playing ->
                        playButton.text = if (playing) getString(R.string.stop_playing) else getString(R.string.play_audio)
                    }
                } else {
                    audioPlayer.stopPlaying()
                    playButton.text = getString(R.string.play_audio)
                }
            }
        }


    }

    private fun requestAudioPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
        } else {
            // Permission has already been granted, start recording directly
            startRecording()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_RECORD_AUDIO_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission was granted, start recording
                    startRecording()
                } else {
                    // Permission denied, show an error message or disable recording functionality
                    Toast.makeText(this, "Permission denied to record audio", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startRecording() {
        val recordButton: Button = findViewById(R.id.recordButton)
        val filePath = "${externalCacheDir?.absolutePath}/audio_record.m4a"
        audioRecorder.startRecording(filePath)
        recordButton.text = getString(R.string.stop_recording)
    }
}