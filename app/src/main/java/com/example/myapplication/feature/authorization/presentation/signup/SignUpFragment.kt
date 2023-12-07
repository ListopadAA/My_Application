package com.example.myapplication.feature.authorization.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.core.doOnStarted
import com.example.myapplication.core.update
import com.example.myapplication.databinding.SigninFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collectLatest

class SignUpFragment : Fragment() {

    private lateinit var binding: SigninFragmentBinding

    private val viewModel: SignUpViewModel by viewModels()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SigninFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        hideBottomBar()
        viewLifecycleOwner.doOnStarted {
            viewModel.state.collectLatest {
                updateUi(it)
            }
        }
        viewLifecycleOwner.doOnStarted {
            viewModel.errorEvent.collectLatest {
                handleEvents(it)
            }
        }
    }

    private fun initViews() = with(binding) {
        etSignUp.doAfterTextChanged {
            viewModel.onLoginChanged(it.toString())
        }
        etSignUpPassword.doAfterTextChanged {
            viewModel.onPasswordChanged(it.toString())
        }
        etSignUpRepeatPassword.doAfterTextChanged {
            viewModel.onRepeatPasswordChanged(it.toString())
        }
        btnSignUp.setOnClickListener {
            viewModel.onSignUpClicked()
        }
    }

    private fun updateUi(loginState: SignUpState) = with(binding) {
        etSignUp.update(loginState.login)
        etSignUpPassword.update(loginState.password)
        etSignUpRepeatPassword.update(loginState.repeatPassword)
    }

    private fun handleEvents(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUp -> {
                auth.createUserWithEmailAndPassword(event.login, event.password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.casesFragment)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Что-то пошло не так",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }

            is SignUpEvent.Error -> {
                Toast.makeText(requireContext(), "Пароли не совпадают", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun hideBottomBar() {
        val view = requireActivity().findViewById<View>(android.R.id.content)
        view.findViewById<View>(R.id.bottom_navigation)?.isVisible = false
    }
}
