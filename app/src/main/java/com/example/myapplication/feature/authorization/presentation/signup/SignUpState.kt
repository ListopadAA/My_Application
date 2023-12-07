package com.example.myapplication.feature.authorization.presentation.signup

data class SignUpState(
    val login: String,
    val password: String,
    val repeatPassword: String
)