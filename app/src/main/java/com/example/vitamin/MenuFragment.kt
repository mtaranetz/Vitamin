package com.example.vitamin

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import java.lang.ClassCastException

public interface buttonClicked {

    public fun buttonClick(number: Number)
}
class MenuFragment : Fragment(), buttonClicked {

    internal var activity: Activity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.let { super.onAttach(it) }
        if (context is Activity) activity = context as Activity
        val view: View = inflater.inflate(R.layout.fragment_menu, container, false)
        //профиль пользователя
        val profile: ImageButton = view.findViewById(R.id.ib_person)
        //музыка
        val music: ImageButton = view.findViewById(R.id.ib_music)
        //авокадная кнопка
        val avocado: ImageButton = view.findViewById(R.id.ib_avocado)

        //штучка со слушателем нажатий была взята отсюда https://habr.com/ru/articles/448744/
        profile.setOnClickListener {
            try{
                (activity as buttonClicked).buttonClick(1)
            }
            catch (ignored: ClassCastException){ }
        }
        avocado.setOnClickListener {
            try{
                (activity as buttonClicked).buttonClick(2)
            }
            catch (ignored: ClassCastException){ }
        }
        music.setOnClickListener {
            try{
                (activity as buttonClicked).buttonClick(3)
            }
            catch (ignored: ClassCastException){ }
        }
        return view
    }
    override fun buttonClick(number: Number) {}
}




