package com.example.myapplication.feature.authorization.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        SignUpState(
            login = "",
            password = "",
            repeatPassword = ""
        )
    )
    val state = _state.asStateFlow()

    private val _errorEvent = MutableSharedFlow<SignUpEvent>()
    val errorEvent: SharedFlow<SignUpEvent> get() = _errorEvent

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

    fun onRepeatPasswordChanged(newRepeatPassword: String) {
        _state.value = _state.value.copy(
            repeatPassword = newRepeatPassword
        )
    }

    fun onSignUpClicked() = with(_state.value) {
        viewModelScope.launch {
            if (login.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                return@launch
            }

            if (password != repeatPassword) {
                _errorEvent.emit(SignUpEvent.Error)
            }

            _errorEvent.emit(SignUpEvent.SignUp(login, password))
        }
    }
}
