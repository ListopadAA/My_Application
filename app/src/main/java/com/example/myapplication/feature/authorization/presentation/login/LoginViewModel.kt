package com.example.myapplication.feature.authorization.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        LoginState(
            login = "",
            password = ""
        )
    )
    val state = _state.asStateFlow()

    private val _errorEvent = MutableSharedFlow<LoginEvent>()
    val errorEvent: SharedFlow<LoginEvent> = _errorEvent

    fun onLoginClicked() = with(_state.value) {
        if (login.isEmpty() || password.isEmpty()) {
            return@with
        }

        viewModelScope.launch {
            _errorEvent.emit(LoginEvent.LogIn(login = login, password = password))
        }
    }

    fun onLoginChanged(newLogin: String) {
        _state.value = _state.value.copy(
            login = newLogin
        )
    }

    fun onPasswordChanged(newPassword: String) {
        _state.value = _state.value.copy(
            password = newPassword
        )
    }
}
