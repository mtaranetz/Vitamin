package com.example.vitamin
import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.identity.util.UUID
import com.example.vitamin.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        checkAccount()
        setupGoogleSignIn()

        // Устанавливаем обработчик нажатия
        binding.registerLink.setOnClickListener {
            // Открываем RegisterFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegisterFragment()) // Убедитесь, что R.id.fragment_container - это ваш контейнер для фрагментов
                .addToBackStack(null)
                .commit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Обработчик нажатия на кнопку "Войти"
        binding.emailSignInButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        binding.bSign.setOnClickListener {
            signWithGoogle()
        }
    }

    private fun setupGoogleSignIn() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.d("MyLog", "Ошибка API: ${e.message}")
            }
        }
    }

    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun signWithGoogle() {
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("MyLog", "Авторизация прошла успешно")
                checkAccount()
            } else {
                Log.d("MyLog", "Ошибка авторизации: ${task.exception?.message}")
            }
        }
    }

    private fun checkAccount() {
        if (auth.currentUser != null) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            val userId = UUID.randomUUID().toString()
            requireContext().getSharedPreferences("UserPreferences", AppCompatActivity.MODE_PRIVATE).edit().clear().apply()
            // Сохраняем user_id в SharedPreferences
            val editor = requireContext().getSharedPreferences("UserPreferences", AppCompatActivity.MODE_PRIVATE).edit()
            editor.putString("user_id", userId)
            editor.apply()

            val username = auth.currentUser?.displayName ?: "Неизвестный"
            val email = auth.currentUser?.email ?: "нет email"
            val dateOfBirth = "1995-03-22" // Пример даты рождения
            val zodiacSignId = 1           // Пример ID знака зодиака
            val preferenceId = 1           // Пример ID предпочтения

            val user = User(userId, username, "NULL", email, dateOfBirth, zodiacSignId, preferenceId)
            sendUserToServer(user)
        }
    }

    private fun sendUserToServer(user: User) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://flaskforvitamin.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.addUser(user)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("MyLog", "Данные успешно отправлены на сервер")
                } else {
                    Log.d("MyLog", "Ошибка при отправке данных: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("MyLog", "Ошибка при подключении к серверу: ${t.message}")
            }
        })
    }

    private fun loginUser(email: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://flaskforvitamin.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val loginRequest = mapOf("email" to email, "password" to password)

        apiService.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!.user

                    // Сохраняем user_id в SharedPreferences
                    val editor = requireContext().getSharedPreferences("UserPreferences", AppCompatActivity.MODE_PRIVATE).edit()
                    editor.putString("user_id", user.user_id)
                    editor.putString("username", user.username)
                    editor.apply()

                    Toast.makeText(requireContext(), "Вход выполнен успешно", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Неверные email или пароль", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка подключения: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
