package com.ort.listapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ort.listapp.databinding.FragmentFamiliaBinding
import com.ort.listapp.utils.HelperClass

class FamiliaFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var binding: FragmentFamiliaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFamiliaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val inputNombre = binding.inputNombre
        val inputCodigo = binding.inputCodigo
        val inputPass = binding.inputPass
        val btnRegister = binding.btnRegister
        val btnAdd = binding.btnAdd

        btnRegister.setOnClickListener {
            authViewModel.registrarFamilia(
                inputNombre.text.toString(),
                inputCodigo.text.toString(),
                inputPass.text.toString(),
            )
        }

        btnAdd.setOnClickListener {
            authViewModel.sumarseEnFamilia(
                inputCodigo.text.toString(),
                inputPass.text.toString(),
            )
        }

        authViewModel.authState.observe(this) {
            if (it.loggedConFamilia) goToMainActivity()
            if (it.errorMessage.isNotBlank()) HelperClass.showToast(
                requireContext(),
                it.errorMessage
            )
            if (it.errorMessage.isNotBlank()) HelperClass.showToast(
                requireContext(),
                it.errorMessage
            )

            btnRegister.isEnabled = it.isDataValid
            btnAdd.isEnabled = it.isDataValid
        }

        inputNombre.doAfterTextChanged {
            authViewModel.familiaDataChanged(
                inputNombre.text.toString(),
                inputPass.text.toString()
            )
        }

        inputPass.doAfterTextChanged {
            authViewModel.familiaDataChanged(
                inputNombre.text.toString(),
                inputPass.text.toString()
            )
        }

    }

    private fun goToMainActivity() {
        val action = FamiliaFragmentDirections.actionFamiliaFragmentToMainActivity()
        binding.root.findNavController().navigate(action)
    }
}