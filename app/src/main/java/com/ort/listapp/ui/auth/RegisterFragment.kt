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
import com.ort.listapp.utils.HelperClass

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
        val inputNombre = binding.inputNombre
        val inputEmail = binding.inputEmail
        val inputPass = binding.inputPass
        val btnRegister = binding.btnRegister

        btnRegister.setOnClickListener {
            authViewModel.registrarUsuario(
                inputNombre.text.toString(),
                inputEmail.text.toString(),
                inputPass.text.toString()
            )
        }

        authViewModel.authState.observe(this) {
            if (it.loggedSinFamilia) goToFamilyFragment()
            if (it.errorMessage.isNotBlank()) HelperClass.showToast(
                requireContext(),
                it.errorMessage
            )

            inputNombre.doAfterTextChanged {
                authViewModel.registerDataChanged(
                    inputNombre.text.toString(),
                    inputEmail.text.toString(),
                    inputPass.text.toString()
                )
            }

            inputEmail.doAfterTextChanged {
                authViewModel.registerDataChanged(
                    inputNombre.text.toString(),
                    inputEmail.text.toString(),
                    inputPass.text.toString()
                )
            }

            inputPass.doAfterTextChanged {
                authViewModel.registerDataChanged(
                    inputNombre.text.toString(),
                    inputEmail.text.toString(),
                    inputPass.text.toString()
                )
            }
        }
    }

    private fun goToFamilyFragment() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToFamiliaFragment()
        binding.root.findNavController().navigate(action)
    }
}

