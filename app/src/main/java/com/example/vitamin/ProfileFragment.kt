package com.example.vitamin

import ApiService
import android.content.SharedPreferences
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        sharedPreferences = requireContext().getSharedPreferences("UserPreferences", AppCompatActivity.MODE_PRIVATE)

        // Проверяем, аутентифицирован ли пользователь через Google
        if (auth.currentUser != null) {
            clearUserPreferences() // Очистка сохраненных данных при смене аккаунта Google
            loadGoogleUserProfile()
        } else {
            loadCachedUserProfile() // Загружаем кэшированные данные или делаем запрос к серверу
        }

        return binding.root
    }

    private fun clearUserPreferences() {
        // Очищаем все данные в SharedPreferences
        sharedPreferences.edit().clear().apply()
    }

    private fun loadGoogleUserProfile() {
        // Загрузка данных из Firebase
        val username = auth.currentUser?.displayName ?: "Неизвестный"
        val dateOfBirth = "Дата не указана" // В Firebase нет даты рождения, можно использовать заглушку
        val photoUrl = auth.currentUser?.photoUrl

        binding.username.text = username
        binding.bdayDay.text = "—"
        binding.bdayMonth.text = dateOfBirth
        if (photoUrl != null) {
            Picasso.get().load(photoUrl).into(binding.avatar)
        }

        // Поскольку у нас нет даты рождения, знак зодиака не отображается
        binding.signView.visibility = View.GONE
    }

    private fun loadCachedUserProfile() {
        val cachedUsername = sharedPreferences.getString("username", null)
        val cachedDateOfBirth = sharedPreferences.getString("date_of_birth", null)
        val cachedZodiacSignId = sharedPreferences.getInt("zodiac_sign_id", -1)

        if (cachedUsername != null && cachedDateOfBirth != null && cachedZodiacSignId != -1) {
            // Данные найдены в кэше
            binding.username.text = cachedUsername
            parseAndDisplayDateOfBirth(cachedDateOfBirth)
            loadZodiacSignImage(cachedZodiacSignId)
        } else {
            // Данные отсутствуют в кэше, делаем запрос к серверу
            loadUserProfileFromDB()
        }
    }

    private fun loadUserProfileFromDB() {
        // Загружаем user_id из SharedPreferences
        val userId = sharedPreferences.getString("user_id", null)
        if (userId != null) {
            clearUserPreferences() // Очистка данных перед загрузкой нового профиля
            fetchUserDataFromServer(userId)
        } else {
            binding.username.text = "Пользователь не найден"
            binding.bdayDay.text = "—"
            binding.bdayMonth.text = "Дата не указана"
        }
    }

    private fun fetchUserDataFromServer(userId: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://flaskforvitamin.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        val request = mapOf("user_id" to userId)
        apiService.getUser(request).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    binding.username.text = user.username
                    parseAndDisplayDateOfBirth(user.date_of_birth)
                    loadZodiacSignImage(user.zodiac_sign_id)

                    // Сохраняем данные в SharedPreferences
                    with(sharedPreferences.edit()) {
                        putString("username", user.username)
                        putString("date_of_birth", user.date_of_birth)
                        putInt("zodiac_sign_id", user.zodiac_sign_id)
                        apply()
                    }
                } else {
                    binding.username.text = "Ошибка загрузки данных"
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                binding.username.text = "Ошибка подключения: ${t.message}"
            }
        })
    }

    private fun parseAndDisplayDateOfBirth(dateOfBirth: String) {
        try {
            val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
            val date = inputFormat.parse(dateOfBirth)

            val dayFormat = SimpleDateFormat("d", Locale.getDefault())
            val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())

            val day = dayFormat.format(date)
            val month = monthFormat.format(date)

            binding.bdayDay.text = day
            binding.bdayMonth.text = month
        } catch (e: Exception) {
            binding.bdayDay.text = "—"
            binding.bdayMonth.text = "Дата не распознана"
        }
    }

    private fun loadZodiacSignImage(zodiacSignId: Int) {
        val signImageUrl = when (zodiacSignId) {
            1 -> getString(R.string.URL_ОВЕН)
            2 -> getString(R.string.URL_ТЕЛЕЦ)
            3 -> getString(R.string.URL_БЛИЗНЕЦЫ)
            4 -> getString(R.string.URL_РАК)
            5 -> getString(R.string.URL_ЛЕВ)
            6 -> getString(R.string.URL_ДЕВА)
            7 -> getString(R.string.URL_ВЕСЫ)
            8 -> getString(R.string.URL_СКОРПИОН)
            9 -> getString(R.string.URL_СТРЕЛЕЦ)
            10 -> getString(R.string.URL_КОЗЕРОГ)
            11 -> getString(R.string.URL_ВОДОЛЕЙ)
            12 -> getString(R.string.URL_РЫБЫ)
            else -> getString(R.string.URL_РЫБЫ)
        }
        Picasso.get().load(signImageUrl).into(binding.signView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
