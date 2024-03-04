package com.example.vitamin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var helloTextView: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val pearsImageView: ImageView = findViewById(R.id.main_pears)
        val pearsTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.pears_animation)
        pearsImageView.startAnimation(pearsTurnAnimation)
        auth = Firebase.auth


        helloTextView = findViewById(R.id.hellou)
        helloTextView.setText(buildString {
        append("Добрый день, ")
    } + auth.currentUser?.displayName +"!")
        val transparencyAnimation = AnimationUtils.loadAnimation(this, R.anim.transparency)
        transparencyAnimation.setStartOffset(1500);
        helloTextView.startAnimation(transparencyAnimation)

        //задержка открытия второго активити
        val mH: Handler = Handler()
        val run = object : Runnable{
            override fun run() {
                val intent = Intent(this@MainActivity, ButtonActivity::class.java)
                startActivity(intent)
            }
        }
        mH.postDelayed(run,4000)



//        pearsImageView.setOnClickListener()
//        {
//        val intent = Intent(this@MainActivity, ButtonActivity::class.java)
//        startActivity(intent)
//        }
    }
}