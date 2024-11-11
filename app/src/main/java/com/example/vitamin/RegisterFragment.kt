package com.example.vitamin

import ApiService
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.identity.util.UUID
import com.example.vitamin.databinding.FragmentRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        sharedPreferences.edit().clear().apply()
        sharedPreferences = requireContext().getSharedPreferences("UserPreferences", AppCompatActivity.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Добавляем TextWatcher для автоматического форматирования даты
        binding.dateOfBirthInput.addTextChangedListener(object : TextWatcher {
            private var isFormatting: Boolean = false
            private var deleteLastChar: Boolean = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                deleteLastChar = count > after
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return

                isFormatting = true

                val formatted = formatDateString(s.toString(), deleteLastChar)
                binding.dateOfBirthInput.setText(formatted)
                binding.dateOfBirthInput.setSelection(formatted.length)

                isFormatting = false
            }

            private fun formatDateString(input: String, isDeleting: Boolean): String {
                val digitsOnly = input.replace(Regex("[^\\d]"), "")
                val length = digitsOnly.length
                val builder = StringBuilder()

                for (i in digitsOnly.indices) {
                    builder.append(digitsOnly[i])
                    if ((i == 3 || i == 5) && length > i + 1 && !isDeleting) {
                        builder.append("-")
                    }
                }

                return builder.toString()
            }
        })

        // Обработчик нажатия на кнопку регистрации
        binding.registerButton.setOnClickListener {
            if (validateInput()) {
                val firstName = binding.firstNameInput.text.toString()
                val lastName = binding.lastNameInput.text.toString()
                val email = binding.emailRegisterInput.text.toString()
                val password = binding.passwordRegisterInput.text.toString()
                val dateOfBirth = binding.dateOfBirthInput.text.toString()

                val userId = UUID.randomUUID().toString()
                val zodiacSignId = getZodiacSign(dateOfBirth)

                // Сохраняем user_id в SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("user_id", userId)
                editor.apply()

                // Создаем объект пользователя
                val user = User(
                    user_id = userId,
                    username = "$firstName $lastName",
                    password = password,
                    email = email,
                    date_of_birth = dateOfBirth,
                    zodiac_sign_id = zodiacSignId,
                    preference_id = 1
                )

                registerUser(user)
            }
        }
    }

    private fun validateInput(): Boolean {
        val email = binding.emailRegisterInput.text.toString()
        val password = binding.passwordRegisterInput.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Введите корректный email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 5) {
            Toast.makeText(requireContext(), "Пароль должен быть не менее 5 символов", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun registerUser(user: User) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://flaskforvitamin.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        apiService.addUser(user).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка подключения: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getZodiacSign(dateOfBirth: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateOfBirth)
        val calendar = Calendar.getInstance()
        calendar.time = date

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1

        return when (month) {
            1 -> if (day < 20) 10 else 11
            2 -> if (day < 19) 11 else 12
            3 -> if (day < 21) 12 else 1
            4 -> if (day < 20) 1 else 2
            5 -> if (day < 21) 2 else 3
            6 -> if (day < 21) 3 else 4
            7 -> if (day < 23) 4 else 5
            8 -> if (day < 23) 5 else 6
            9 -> if (day < 23) 6 else 7
            10 -> if (day < 23) 7 else 8
            11 -> if (day < 22) 8 else 9
            12 -> if (day < 22) 9 else 10
            else -> 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
