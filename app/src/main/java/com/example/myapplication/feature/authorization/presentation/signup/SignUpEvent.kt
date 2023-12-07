package com.example.myapplication.feature.authorization.presentation.signup

sealed interface SignUpEvent {

    data object Error : SignUpEvent

    data class SignUp(
        val login: String,
        val password: String
    ) : SignUpEvent
}