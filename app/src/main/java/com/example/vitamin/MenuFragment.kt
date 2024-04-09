package com.example.vitamin

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.ClassCastException

public interface buttonClicked {

    public fun buttonClick(number: Number)
}
class MenuFragment : Fragment(), buttonClicked {

    private val button_num = 0
    internal var activity: Activity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.let { super.onAttach(it) }
        if (context is Activity) activity = context as Activity
        val view: View = inflater.inflate(R.layout.fragment_menu, container, false)
        //профиль пользователя
        val profile: CircleImageView = view.findViewById(R.id.person1)
        val avocado: ImageButton = view.findViewById(R.id.ib_avocado)
        //другие разделы
        val person2: ImageButton = view.findViewById(R.id.person2)
        val person3: ImageButton = view.findViewById(R.id.person3)
        val person4: ImageButton = view.findViewById(R.id.person4)

        //штучка со слушателем нажатий была взята отсюда https://habr.com/ru/articles/448744/
        profile.setOnClickListener {
            try{
                (activity as buttonClicked).buttonClick(1)
            }
            catch (ignored: ClassCastException){ }
        }
        avocado.setOnClickListener {
            try{
                (activity as buttonClicked).buttonClick(5)
            }
            catch (ignored: ClassCastException){ }
        }

        person2.setOnClickListener {
            try{
                (activity as buttonClicked).buttonClick(2)
            }
            catch (ignored: ClassCastException){ }
        }
        person3.setOnClickListener {
            try{
                (activity as buttonClicked).buttonClick(3)
            }
            catch (ignored: ClassCastException){ }
        }
        person4.setOnClickListener {
            try{
                (activity as buttonClicked).buttonClick(4)
            }
            catch (ignored: ClassCastException){ }
        }
        return view
    }
    override fun buttonClick(number: Number) {}
}




