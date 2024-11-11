package com.example.vitamin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch

class MusicFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_music, container, false)
        val switchMusic: Switch = view.findViewById(R.id.sw_music)

        // Инициализация MediaPlayer
        MusicPlayer.initialize(requireContext())

        // Проверяем текущее состояние музыки
        switchMusic.isChecked = MusicPlayer.isPlaying

        // Обработчик нажатия на переключатель
        switchMusic.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                MusicPlayer.startMusic()
            } else {
                MusicPlayer.pauseMusic()
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // MusicPlayer не освобождается, чтобы музыка продолжала играть
    }
}
