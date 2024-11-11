package com.example.vitamin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vitamin.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Открываем `LoginFragment` при запуске Activity
        if (savedInstanceState == null) {
            openFragment(LoginFragment())
        }
    }

    // Функция для открытия фрагмента
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        Log.d("FragmentTransaction", "Fragment ${fragment::class.java.simpleName} открыт")
    }
}
