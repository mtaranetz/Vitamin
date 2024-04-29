package com.example.vitamin

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
class MusicFragment : Fragment() {
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_music, container, false)

        val switchMusic: Switch = view.findViewById(R.id.sw_music)
        switchMusic.setOnClickListener {
            if (isPlaying) {
                mediaPlayer?.pause()
                isPlaying = false
                switchMusic.isChecked = false
            } else {
                mediaPlayer?.start()
                isPlaying = true
                switchMusic.isChecked = true
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Инициализация медиаплеера
        mediaPlayer = MediaPlayer.create(context, R.raw.cat_meow)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}