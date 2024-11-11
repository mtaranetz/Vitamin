package com.example.vitamin

data class User(
    val user_id: String,        // Добавлен user_id
    val username: String,
    val password: String,       // Не забудьте хэшировать пароль в серверном приложении
    val email: String,
    val date_of_birth: String,
    val zodiac_sign_id: Int,
    val preference_id: Int
)
