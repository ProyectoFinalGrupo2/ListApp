package com.ort.listapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ort.listapp.databinding.FragmentForgotBinding
import com.ort.listapp.utils.HelperClass
import com.ort.listapp.utils.HelperClass.showToast

class ForgotFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var binding: FragmentForgotBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val tfEmail = binding.tfEmail
        val inputEmail = tfEmail.editText
        val btnEnviar = binding.btnEnviar

        btnEnviar.setOnClickListener {
            if (inputEmail != null) {
                if (!HelperClass.isEmailValid(inputEmail.text.toString().trim())) {
                    tfEmail.error = "Email invalido"
                } else {
                    authViewModel.passwordReset(inputEmail.text.toString().trim())
                }
            }
        }

        authViewModel.authState.observe(this) {
            if (it.errorMessage.isNotBlank()) {
                showToast(requireContext(), it.errorMessage)
                authViewModel.authState.postValue(AuthState())
                goToLoginFragment()
            }
        }

    }

    private fun goToLoginFragment() {
        val action = ForgotFragmentDirections.actionForgotFragmentToLoginFragment()
        binding.root.findNavController().navigate(action)
    }

}