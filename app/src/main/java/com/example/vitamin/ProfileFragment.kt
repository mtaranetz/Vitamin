package com.example.vitamin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    //override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)
    //}

//    fun setDescription(buttonIndex: Int) {
//        when (buttonIndex) {
//            1 -> profile_sign.setImageResource(R.drawable.logo)
//            //2 -> catImageView.setImageResource(R.drawable.cat2)
//            //3 -> catImageView.setImageResource(R.drawable.cat3)
//            else -> {
//            }
//        }
//    }
}