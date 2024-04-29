package com.example.vitamin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ButtonActivity : AppCompatActivity(), buttonClicked {
    lateinit var auth: FirebaseAuth

    var frag_profile: ProfileFragment = ProfileFragment()
    var frag_cards: CardsFragment = CardsFragment()
    var frag_solution: SolutionFragment = SolutionFragment()
    var frag_horoscope: HoroscopeFragment = HoroscopeFragment()
    var frag_prank: PrankFragment = PrankFragment()
    var frag_quotes = QuotesFragment()
    var frag_motivation = MotivationFragment()
    var frag_about_us = AboutUsFragment()
    val frag_sign = SignFragment()
    val frag_music = MusicFragment()



    private lateinit var fTrans: FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContentView(R.layout.activity_buttons)
        }

    override fun buttonClick(number: Number) {
        fTrans = supportFragmentManager.beginTransaction()
        when (number) {
            //-----------------МЕНЮ
            1 -> fTrans.replace(R.id.main_fragment, frag_profile) //профиль
            2 -> fTrans.replace(R.id.main_fragment, frag_cards) //главный экран
            3 -> {
                fTrans.replace(R.id.main_fragment, frag_music)
                fTrans.addToBackStack(null)
            } //музыка
            //-----------------КАРТОЧКИ
            6 -> { //кубик принятия решений
                fTrans.replace(R.id.main_fragment, frag_solution)
            }
            7 -> { //гороскоп
                fTrans.replace(R.id.main_fragment, frag_horoscope)
            }
            8 -> { //анекдот
                fTrans.replace(R.id.main_fragment, frag_prank)
            }
            9 -> { //цитаты
                fTrans.replace(R.id.main_fragment, frag_quotes)
            }

            10 -> { //мотиватор
                fTrans.replace(R.id.main_fragment, frag_motivation)
            }

            11 -> { //в раздел об авторах
                fTrans.replace(R.id.main_fragment, frag_about_us)
            }

            12 -> { //знак судьбы
                fTrans.replace(R.id.main_fragment, frag_sign)
            }
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


