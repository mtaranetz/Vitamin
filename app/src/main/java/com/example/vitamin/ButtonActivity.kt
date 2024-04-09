package com.example.vitamin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ButtonActivity : AppCompatActivity(), buttonClicked {
    lateinit var auth: FirebaseAuth
    var frag_menu: MenuFragment = MenuFragment()
    var frag_profile: ProfileFragment = ProfileFragment()
    var frag_cards: CardsFragment = CardsFragment()
    var frag_menu1: TestFragment = TestFragment()
    private lateinit var fTrans: FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_buttons)
    }
    override fun buttonClick(number: Number) {
//        fTrans = supportFragmentManager.beginTransaction()
//        fTrans.replace(R.id.fragment_menu, frag_menu1)
//        fTrans.commit()

        fTrans = supportFragmentManager.beginTransaction()
        when (number) {
            1 -> fTrans.replace(R.id.main_fragment, frag_profile)
            //сюда нужно добавить разделы
//            2 -> fTrans.replace(R.id.fragment_menu, frag_menu1)
//            3 -> fTrans.replace(R.id.fragment_menu, frag_menu1)
//            4 -> fTrans.replace(R.id.fragment_menu, frag_menu1)
            5 -> fTrans.replace(R.id.main_fragment, frag_cards)
        }
            fTrans.commit()
    }


//        public fun onClick(v: View) {
//            fTrans = supportFragmentManager.beginTransaction()
//            if (v.id == R.id.fragment_menu) fTrans.replace(R.id.fragment_menu, frag_menu1)
//            fTrans.commit()
//        }



    //решение требует времени
        //Glide.with(getApplicationContext()).load(auth.currentUser?.photoUrl).into(accountImage);
//        Picasso.get().load(auth.currentUser?.photoUrl).into(object : com.squareup.picasso.Target {
//            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//                // loaded bitmap is here (bitmap)
//
//                accountImage.setImageBitmap(bitmap)
//            }
//
//            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
//
//            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
//        })
    }


