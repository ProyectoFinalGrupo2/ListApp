package com.ort.listapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ort.listapp.databinding.FragmentLoginBinding
import com.ort.listapp.utils.HelperClass.showToast

class LoginFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val inputEmail = binding.inputEmail
        val inputPass = binding.inputPass
        val btnLogin = binding.btnLogin
        val btnRegister = binding.btnRegister

//        val currentUser = auth.currentUser
        val currentUser = null
        if (currentUser != null) {
//            goToMainActivity()
        } else {
            btnRegister.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                binding.root.findNavController().navigate(action)
            }

            btnLogin.setOnClickListener {
                authViewModel.login(
                    inputEmail.text.toString(),
                    inputPass.text.toString()
                )
            }

            authViewModel.authState.observe(this@LoginFragment) {
                if (it.loggedSinFamilia) goToFamilyFragment()
                if (it.loggedConFamilia) goToMainActivity()
                if (it.errorMessage.isNotBlank()) {
                    showToast(requireContext(), it.errorMessage)
                    authViewModel.authState.postValue(AuthState())
                }
                btnLogin.isEnabled = it.isDataValid
            }

            inputEmail.doAfterTextChanged {
                authViewModel.loginDataChanged(
                    inputEmail.text.toString(),
                    inputPass.text.toString()
                )
            }

            inputPass.doAfterTextChanged {
                authViewModel.loginDataChanged(
                    inputEmail.text.toString(),
                    inputPass.text.toString()
                )
            }
        }
    }

    private fun checkIfUserIsAuthenticated(): Boolean =
        authViewModel.checkIfUserIsAuthenticated()

    private fun goToMainActivity() {
        val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
        binding.root.findNavController().navigate(action)
    }

    private fun goToFamilyFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToFamiliaFragment()
        binding.root.findNavController().navigate(action)
    }
}