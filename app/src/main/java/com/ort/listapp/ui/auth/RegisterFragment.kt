package com.ort.listapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ort.listapp.databinding.FragmentRegisterBinding
import com.ort.listapp.utils.HelperClass.isEmailValid
import com.ort.listapp.utils.HelperClass.showToast

class RegisterFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val tfNombre = binding.tfNombre
        val inputNombre = tfNombre.editText
        val tfEmail = binding.tfEmail
        val inputEmail = tfEmail.editText
        val tfPass = binding.tfPass
        val inputPass = tfPass.editText
        val btnRegister = binding.btnRegister

        btnRegister.setOnClickListener {
            if (inputNombre != null && inputEmail != null && inputPass != null) {
                tfNombre.helperText = null
                tfPass.helperText = null
                if (inputNombre.text.isEmpty()) {
                    tfNombre.error = "El nombre no puede estar vacio"
                } else if (!isEmailValid(inputEmail.text.toString().trim())) {
                    tfEmail.error = "Email invalido"
                } else if (inputPass.text.isBlank()) {
                    tfPass.error = "Debe ingresar una contraseña"
                } else {
                    authViewModel.registrarUsuario(
                        inputNombre.text.toString(),
                        inputEmail.text.toString(),
                        inputPass.text.toString()
                    )
                }
            }
        }

        authViewModel.authState.observe(this) {
            if (it.loggedSinFamilia) goToFamilyFragment()
            if (it.errorMessage.isNotBlank()) showToast(
                requireContext(),
                it.errorMessage
            )
            btnRegister.isEnabled = it.isDataValid
        }

        inputNombre?.doAfterTextChanged {
            tfNombre.error = null
            if (inputNombre.text.length < 3) {
                tfNombre.helperText = "Ingrese 3 o más caracteres"
            } else tfNombre.helperText = null
        }

        inputEmail?.doAfterTextChanged {
            tfEmail.error = null
            if (!isEmailValid(inputEmail?.text.toString().trim())) {
                tfEmail.helperText = "Ingrese un email válido"
            } else tfEmail.helperText = null
        }

        inputPass?.doAfterTextChanged {
            tfPass.error = null
            if (inputPass.text.length < 5) {
                tfPass.helperText = "Ingrese 5 o más caracteres"
            } else tfPass.helperText = null
        }
    }

    private fun goToFamilyFragment() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToFamiliaFragment()
        binding.root.findNavController().navigate(action)
    }
}

