package com.ort.listapp.ui.config

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ort.listapp.ListaAppApplication.Companion.prefsHelper
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentUserConfigBinding
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.auth.AuthViewModel
import com.ort.listapp.utils.HelperClass
import com.ort.listapp.utils.HelperClass.isEmailValid
import com.ort.listapp.utils.HelperClass.showToast

@Suppress("DEPRECATION")
class UserConfigFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private val familyViewModel: FamilyViewModel by activityViewModels()

    private lateinit var binding: FragmentUserConfigBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserConfigBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tool_bar_config, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnVolver -> {
                binding.root.findNavController().navigateUp()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        val nombreUsuario = binding.nombreUsuario
        val email = binding.email
        val tfNewEmail = binding.tfNewEmail
        val inputEmail = tfNewEmail.editText
        val btnCambiarEmail = binding.btnCambiarEmail
        val nombreFamilia = binding.nombreFamilia
        val codigoFamilia = binding.codigoFamilia
        val passFamilia = binding.passFamilia
        val btnSalirFamilia = binding.btnSalirFamilia
        val btnCerrarSesion = binding.btnCerrarSesion

        val familia = familyViewModel.getFamilia().value
        val userName = prefsHelper.getUserName()
        val userEmail = prefsHelper.getUserEmail()

        if (familia != null) {
            nombreUsuario.text = "Nombre del usuario: $userName"
            email.text = "Email: $userEmail"
            nombreFamilia.text = "Familia: ${familia.nombre}"
            codigoFamilia.text = "Código: ${familia.id}"
            passFamilia.text = "Contraseña: ${familia.password}"
        }

        btnCambiarEmail.setOnClickListener {
            if (inputEmail != null) {
                if (!isEmailValid(inputEmail.text.toString().trim())) {
                    tfNewEmail.error = "Email invalido"
                } else {
                    btnCambiarEmail.icon = HelperClass.getCircularProgress(requireContext())
                    btnCambiarEmail.isClickable = false
                    authViewModel.changeEmail(
                        inputEmail.text.toString(),
                    )
                }
            }
        }

        btnSalirFamilia.setOnClickListener {
            authViewModel.borrarseDeFamilia()
        }

        btnCerrarSesion.setOnClickListener {
            authViewModel.logout()
        }

        authViewModel.authState.observe(this) {
            if (it.successMessage.isNotBlank()) {
                showToast(
                    requireContext(),
                    it.successMessage
                )
                authViewModel.logout()
            }
            if (it.errorMessage.isNotBlank()) showToast(
                requireContext(),
                it.errorMessage
            )
        }

        inputEmail?.doAfterTextChanged {
            tfNewEmail.error = null
            if (!isEmailValid(inputEmail.text.toString().trim())) {
                tfNewEmail.helperText = "Ingrese un email válido"
            } else tfNewEmail.helperText = null
        }
    }

}