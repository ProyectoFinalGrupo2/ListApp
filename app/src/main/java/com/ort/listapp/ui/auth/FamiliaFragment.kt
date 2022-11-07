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
import com.ort.listapp.utils.HelperClass.showToast

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
        val inputRegPass = binding.inputRegPass
        val inputAddPass = binding.inputAddPass
        val btnRegister = binding.btnRegister
        val btnAdd = binding.btnAdd

        btnRegister.setOnClickListener {
            authViewModel.registrarFamilia(
                inputNombre.text.toString(),
                inputRegPass.text.toString(),
            )
        }

        btnAdd.setOnClickListener {
            authViewModel.sumarseEnFamilia(
                inputCodigo.text.toString(),
                inputAddPass.text.toString(),
            )
        }

        authViewModel.authState.observe(this) {
            if (it.loggedConFamilia) goToMainActivity()
            if (it.errorMessage.isNotBlank()) showToast(
                requireContext(),
                it.errorMessage
            )
            btnRegister.isEnabled = it.isDataValid
            btnAdd.isEnabled = it.isDataValid
        }

        inputNombre.doAfterTextChanged {
            authViewModel.familiaDataChanged(
                inputNombre.text.toString(),
                inputRegPass.text.toString()
            )
        }

        inputRegPass.doAfterTextChanged {
            authViewModel.familiaDataChanged(
                inputNombre.text.toString(),
                inputRegPass.text.toString()
            )
        }

        inputCodigo.doAfterTextChanged {
            authViewModel.familiaDataChanged(
                inputCodigo.text.toString(),
                inputAddPass.text.toString()
            )
        }

        inputAddPass.doAfterTextChanged {
            authViewModel.familiaDataChanged(
                inputCodigo.text.toString(),
                inputAddPass.text.toString()
            )
        }

    }

    private fun goToMainActivity() {
        val action = FamiliaFragmentDirections.actionFamiliaFragmentToMainActivity()
        binding.root.findNavController().navigate(action)
    }
}