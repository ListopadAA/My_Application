package com.example.myapplication.feature.cases

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.CasesFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class CasesFragment : Fragment(R.layout.cases_fragment) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var binding: CasesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CasesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCasesLogOut.setOnClickListener {
            auth.signOut()
            requireContext().startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }
}
