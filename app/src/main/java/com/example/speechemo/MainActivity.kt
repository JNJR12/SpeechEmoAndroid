package com.example.speechemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        val btnRecordAudio: Button = findViewById(R.id.btnRecordAudio)
        btnRecordAudio.setOnClickListener {
            val intent = Intent(this, RecordAudioActivity::class.java)
            startActivity(intent)
        }

        val btnUploadAudio: Button = findViewById(R.id.btnUploadAudio)
        btnUploadAudio.setOnClickListener {
            val intent = Intent(this, UploadAudioActivity::class.java)
            startActivity(intent)
        }
    }
}
