package com.example.vitamin

import android.content.Context
import android.media.MediaPlayer

object MusicPlayer {
    private var mediaPlayer: MediaPlayer? = null
    var isPlaying: Boolean = false

    fun initialize(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.cat_meow)
            mediaPlayer?.isLooping = true
        }
    }

    fun startMusic() {
        mediaPlayer?.start()
        isPlaying = true
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
        isPlaying = false
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
    }
}
