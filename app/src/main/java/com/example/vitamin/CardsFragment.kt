package com.example.vitamin

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import java.lang.ClassCastException

class CardsFragment : Fragment(), buttonClicked {
    internal var activity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.let { super.onAttach(it) }
        if (context is Activity) activity = context as Activity
        val view: View = inflater.inflate(R.layout.fragment_cards, container, false)
        val solution: ImageButton = view.findViewById(R.id.ib_kubik)
        val horoscope: ImageButton = view.findViewById(R.id.ib_goroskop)
        val prank: ImageButton = view.findViewById(R.id.ib_anekdot)
        val qoute = view.findViewById<ImageButton>(R.id.ib_quote)
        val motivation = view.findViewById<ImageButton>(R.id.ib_motivation)
        val with_love = view.findViewById<TextView>(R.id.tv_with_love)
        val sign = view.findViewById<ImageButton>(R.id.ib_sign)

        solution.setOnClickListener {
            try{
                (activity as buttonClicked).buttonClick(6)
            }
            catch (ignored: ClassCastException){ }
        }

        horoscope.setOnClickListener {
            try{
                (activity as buttonClicked).buttonClick(7)
            }
            catch (ignored: ClassCastException){ }
        }

        prank.setOnClickListener {
            try{
                (activity as buttonClicked).buttonClick(8)
            }
            catch (ignored: ClassCastException){ }
        }

        qoute.setOnClickListener{
            try {
                (activity as buttonClicked).buttonClick(9)
            }
            catch (ignored: ClassCastException){ }
        }

        motivation.setOnClickListener{
            try {
                (activity as buttonClicked).buttonClick(10)
            }
            catch (ignored: ClassCastException){ }
        }

        with_love.setOnClickListener{
            try {
                (activity as buttonClicked).buttonClick(11)
            }
            catch (ignored: ClassCastException){ }
        }

        sign.setOnClickListener{
            try {
                (activity as buttonClicked).buttonClick(12)
            }
            catch (ignored: ClassCastException){ }
        }

        return view

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val runningString = view.findViewById<LottieAnimationView>(R.id.running_string)
        runningString.repeatCount = LottieDrawable.INFINITE
        runningString.playAnimation()
    }

    override fun buttonClick(number: Number) { }
}