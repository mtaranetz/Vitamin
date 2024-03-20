package com.example.vitamin

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val i: ImageView = findViewById(R.id.sign_view)
        val filename = "Pisces.png"
        try {
            applicationContext.assets.open(filename).use { inputStream ->
                val drawable =
                    Drawable.createFromStream(inputStream, null)
                i.setImageDrawable(drawable)
                i.setScaleType(ImageView.ScaleType.FIT_XY)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

