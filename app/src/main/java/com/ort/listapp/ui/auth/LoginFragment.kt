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
import com.ort.listapp.utils.HelperClass.isEmailValid
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
        val tfEmail = binding.tfEmail
        val inputEmail = tfEmail.editText
        val tfPass = binding.tfPass
        val inputPass = tfPass.editText
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
                if (inputEmail != null && inputPass != null) {
                    if (!isEmailValid(inputEmail.text.toString().trim())) {
                        tfEmail.error = "Email invalido"
                    } else if (inputPass.text.isBlank()) {
                        tfPass.error = "Debe ingresar una contraseña"
                    } else {
                        authViewModel.login(
                            inputEmail.text.toString().trim(),
                            inputPass.text.toString()
                        )
                    }
                }
            }

            authViewModel.authState.observe(this) {
                if (it.loggedSinFamilia) goToFamilyFragment()
                if (it.loggedConFamilia) goToMainActivity()
                if (it.errorMessage.isNotBlank()) {
                    showToast(requireContext(), it.errorMessage)
                    authViewModel.authState.postValue(AuthState())
                }
                inputEmail?.text?.clear()
                inputPass?.text?.clear()
            }

            inputEmail?.doAfterTextChanged {
                tfEmail.error = null
            }

            inputPass?.doAfterTextChanged {
                tfPass.error = null
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