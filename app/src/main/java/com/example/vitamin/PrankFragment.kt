package com.example.vitamin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.squareup.picasso.Picasso
import java.util.Random

class PrankFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imagesRef = storageRef.child("pranks")

        val prankLoad = view.findViewById<LottieAnimationView>(R.id.prank_loading)
        prankLoad.repeatCount = LottieDrawable.INFINITE
        prankLoad.playAnimation()
        view.findViewById<ImageView>(R.id.iv_prank).isVisible = false

        imagesRef.listAll()
            .addOnSuccessListener(OnSuccessListener<ListResult> { listResult ->
                val images = listResult.items
                val randomIndex = Random().nextInt(images.size)
                val selectedImage = images[randomIndex]

                // Получаем ссылку на изображение
                selectedImage.downloadUrl.addOnSuccessListener { uri ->
                    // Загружаем изображение в ImageView с помощью Picasso
                    val imageView: ImageView = view.findViewById(R.id.iv_prank)
                    Picasso.get().load(uri).into(imageView)
                    view.findViewById<ImageView>(R.id.iv_prank).isVisible = true
                    prankLoad.isVisible = false
                }
            })

        val hedgehog = view.findViewById<LottieAnimationView>(R.id.hedgehog_animation)
        hedgehog.repeatCount = LottieDrawable.INFINITE
        hedgehog.playAnimation()

        super.onViewCreated(view, savedInstanceState)
    }
}