package com.ort.listapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ort.listapp.databinding.FragmentFamiliaBinding
import com.ort.listapp.utils.HelperClass
import com.ort.listapp.utils.HelperClass.showToast

class FamiliaFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var binding: FragmentFamiliaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFamiliaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val tfNombre = binding.tfNombre
        val inputNombre = tfNombre.editText
        val tfCodigo = binding.tfCodigo
        val inputCodigo = tfCodigo.editText
        val tfRegPass = binding.tfRegPass
        val inputRegPass = tfRegPass.editText
        val tfAddPass = binding.tfAddPass
        val inputAddPass = tfAddPass.editText
        val btnRegister = binding.btnRegister
        val btnAdd = binding.btnAdd

        inputNombre?.doAfterTextChanged {
            tfNombre.error = null
            if (inputNombre.text.length < 3) {
                tfNombre.helperText = "Ingrese 3 o más caracteres"
            } else tfNombre.helperText = null
        }

        inputRegPass?.doAfterTextChanged {
            tfRegPass.error = null
            if (inputRegPass.text.length < 5) {
                tfRegPass.helperText = "Ingrese 5 o más caracteres"
            } else tfRegPass.helperText = null
        }

        btnRegister.setOnClickListener {
            if (inputNombre != null && inputRegPass != null) {
                tfNombre.helperText = null
                tfRegPass.helperText = null
                if (inputNombre.text.isEmpty()) {
                    tfNombre.error = "El nombre no puede estar vacio"
                } else if (inputRegPass.text.isBlank()) {
                    tfRegPass.error = "Debe ingresar una contraseña"
                } else {
                    btnRegister.icon = HelperClass.getCircularProgress(requireContext())
                    btnRegister.isClickable = false
                    authViewModel.registrarFamilia(
                        inputNombre.text.toString(),
                        inputRegPass.text.toString(),
                    )
                }
            }
        }

        fun isCodigoValid(inputCodigo: EditText) =
            inputCodigo.text.length == 6

        btnAdd.setOnClickListener {
            if (inputCodigo != null && inputAddPass != null) {
                if (!isCodigoValid(inputCodigo)) {
                    tfCodigo.error = "Código inválido"
                } else if (inputAddPass.text.isBlank()) {
                    tfAddPass.error = "Debe ingresar una contraseña"
                } else {
                    btnAdd.icon = HelperClass.getCircularProgress(requireContext())
                    btnAdd.isClickable = false
                    authViewModel.sumarseEnFamilia(
                        inputCodigo.text.toString(),
                        inputAddPass.text.toString(),
                    )
                }
            }
        }

        inputCodigo?.doAfterTextChanged {
            tfCodigo.error = null
            if (!isCodigoValid(inputCodigo)) {
                tfCodigo.helperText = "Ingrese los 6 caracteres provistos"
            } else tfCodigo.helperText = null
        }

        inputAddPass?.doAfterTextChanged {
            tfAddPass.error = null
        }

        authViewModel.authState.observe(this) {
            if (it.loggedConFamilia) goToMainActivity()
            if (it.errorMessage.isNotBlank()) showToast(
                requireContext(),
                it.errorMessage
            )
            btnRegister.icon = null
            btnRegister.isClickable = true
            btnAdd.icon = null
            btnAdd.isClickable = true
        }
    }

    private fun goToMainActivity() {
        val action = FamiliaFragmentDirections.actionFamiliaFragmentToMainActivity()
        binding.root.findNavController().navigate(action)
        requireActivity().finish()
    }
}