package com.example.vitamin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlin.random.Random

class QuotesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quotes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val q = view.findViewById<TextView>(R.id.tv_quote)
        val x = Random(5)
        when (x.nextInt(13)) {
            1 -> q!!.setText(R.string.prank1)
            2 -> q!!.setText(R.string.prank2)
            3 -> q!!.setText(R.string.prank3)
            4 -> q!!.setText(R.string.prank4)
            5 -> q!!.setText(R.string.prank5)
            6 -> q!!.setText(R.string.prank6)
            7 -> q!!.setText(R.string.prank7)
            8 -> q!!.setText(R.string.prank8)
            9 -> q!!.setText(R.string.prank9)
            10 -> q!!.setText(R.string.prank10)
            11 -> q!!.setText(R.string.prank11)
            12 -> q!!.setText(R.string.prank12)
            13 -> q!!.setText(R.string.prank13)
        }
        x.nextInt()
        super.onViewCreated(view, savedInstanceState)
    }

}