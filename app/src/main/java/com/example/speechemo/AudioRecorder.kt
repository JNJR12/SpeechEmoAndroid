package com.example.speechemo
import android.media.MediaRecorder
import java.io.IOException

class AudioRecorder {
    private var mediaRecorder: MediaRecorder? = null
    private var currentFilePath: String? = null
    var isRecording: Boolean = false
        private set

    fun startRecording(filePath: String){
        currentFilePath = filePath
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            // Set Output Format
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(filePath)
            try{
                prepare()
                start()
                isRecording = true
            }
            catch (e:IOException){
                e.printStackTrace()
            }
        }
    }

    fun stopRecording(){
        if(isRecording){
            mediaRecorder?.apply{
                stop()
                release()
            }
            mediaRecorder = null
            isRecording = false
        }
    }
}