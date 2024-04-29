package com.example.vitamin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vitamin.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        binding.apply {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/vitamin-database." +
                    "appspot.com/o/profileSigns%2FProperty%201%3D%D0%91%D0%BB%D0%B8%D0%B7%D0%BD%D" +
                    "0%B5%D1%86%D1%8B.png?alt=media&token=1170e80b-751f-4749-bb91-ddbd49589a8c")
                .into(signView)
            username.text = auth.currentUser?.displayName
            Picasso.get().load(auth.currentUser?.photoUrl).into(avatar)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

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