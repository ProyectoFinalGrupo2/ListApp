package com.ort.listapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ort.listapp.ListaAppApplication.Companion.prefsHelper
import com.ort.listapp.databinding.FragmentLoginBinding
import com.ort.listapp.utils.HelperClass.getCircularProgress
import com.ort.listapp.utils.HelperClass.isEmailValid
import com.ort.listapp.utils.HelperClass.logErrorMessage
import com.ort.listapp.utils.HelperClass.showToast

class LoginFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logErrorMessage("LoginFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        logErrorMessage("LoginFragment onCreateView")
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logErrorMessage("LoginFragment onViewCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        logErrorMessage("LoginFragment onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        logErrorMessage("LoginFragment onStart")

        val tfEmail = binding.tfEmail
        val inputEmail = tfEmail.editText
        val tfPass = binding.tfPass
        val inputPass = tfPass.editText
        val btnLogin = binding.btnLogin
        val btnRegister = binding.btnRegister
        val btnOlv = binding.btnOlv

        if (authViewModel.checkIfUserIsAuthenticated() &&
            prefsHelper.getFamilyId().isBlank()
        ) authViewModel.logout()

        if (authViewModel.checkIfUserIsAuthenticated()) {
            goToMainActivity()
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
                        btnLogin.icon = getCircularProgress(requireContext())
                        btnLogin.isClickable = false
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
                    btnLogin.icon = null
                    btnLogin.isClickable = true
                    inputEmail?.setText("")
                    inputPass?.setText("")
                    authViewModel.authState.postValue(AuthState())
                }
            }

            inputEmail?.doAfterTextChanged {

                tfEmail.error = null
            }

            inputPass?.doAfterTextChanged {
                tfPass.error = null
            }

            btnOlv.setOnClickListener {
                goToForgotFragment()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        logErrorMessage("LoginFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        logErrorMessage("LoginFragment onPause")
    }

    override fun onStop() {
        super.onStop()
        logErrorMessage("LoginFragment onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logErrorMessage("LoginFragment onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logErrorMessage("LoginFragment onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        logErrorMessage("LoginFragment onDestroy")
    }

    private fun checkIfUserIsAuthenticated(): Boolean =
        authViewModel.checkIfUserIsAuthenticated()

    private fun goToMainActivity() {
        val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
        binding.root.findNavController().navigate(action)
        requireActivity().finish()
    }

    private fun goToFamilyFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToFamiliaFragment()
        binding.root.findNavController().navigate(action)
    }

    private fun goToForgotFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToForgotFragment()
        binding.root.findNavController().navigate(action)
    }
}