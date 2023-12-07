package com.example.myapplication.feature.authorization.presentation.login

sealed interface LoginEvent {

    data class LogIn(
        val login: String,
        val password: String
    ) : LoginEvent
}