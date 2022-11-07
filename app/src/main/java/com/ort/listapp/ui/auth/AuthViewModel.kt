package com.ort.listapp.ui.auth

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.listapp.ListaAppApplication.Companion.prefsHelper
import com.ort.listapp.data.AuthRepository
import com.ort.listapp.data.FamiliaRepository
import com.ort.listapp.data.UsuarioRepository
import com.ort.listapp.utils.HelperClass.getRandomCode
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    val authState = MutableLiveData<AuthState>()
    private val authRepository = AuthRepository()
    private val familiaRepository = FamiliaRepository()
    private val usuarioRepository = UsuarioRepository()

    fun registrarUsuario(nombre: String, email: String, pass: String) {
        viewModelScope.launch {
            val authResponse = authRepository.createUserWithEmailAndPassword(nombre, email, pass)
            if (authResponse.errorMessage.isBlank()) {
                val usuarioNuevo = authResponse.usuario
                prefsHelper.saveUserName(usuarioNuevo.nombre)
                prefsHelper.saveUserId(usuarioNuevo.uid)
                authState.postValue(AuthState(loggedSinFamilia = true))
            } else {
                authState.postValue(AuthState(errorMessage = authResponse.errorMessage))
            }
        }
    }

    fun registerDataChanged(nombre: String, email: String, pass: String) {
        if (isNombreValid(nombre) && isEmailValid(email) && isPasswordValid(pass))
            authState.postValue(AuthState(isDataValid = true))
        else authState.postValue(AuthState(isDataValid = false))
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            val authResponse = authRepository.signInWithEmailAndPassword(email, pass)
            if (authResponse.errorMessage.isBlank()) {
                val usuario = authResponse.usuario
                prefsHelper.saveUserName(usuario.nombre)
                prefsHelper.saveUserId(usuario.uid)
                if (usuario.familia.isBlank()) {
                    authState.postValue(AuthState(loggedSinFamilia = true))
                } else {
                    authState.postValue(AuthState(loggedConFamilia = true))
                    prefsHelper.saveFamilyId(usuario.familia)
                }
            } else {
                authState.postValue(AuthState(errorMessage = authResponse.errorMessage))
            }
        }
    }

    fun checkIfUserIsAuthenticated(): Boolean = authRepository.checkIfUserIsAuthenticated()

    fun logout() = authRepository.logout()

    fun registrarFamilia(nombre: String, passFamilia: String) {
        viewModelScope.launch {
            var idFamilia = ""
            do {
                idFamilia = getRandomCode(6)
            } while (familiaRepository.existeFamilia(idFamilia))
            familiaRepository.crearFamilia(nombre, idFamilia, passFamilia)
            usuarioRepository.agregarFamiliaEnUsuario(prefsHelper.getUserId(), idFamilia)
            prefsHelper.saveFamilyId(idFamilia)
            authState.postValue(AuthState(loggedConFamilia = true))
        }
    }

    fun sumarseEnFamilia(idFamilia: String, passFamilia: String) {
        viewModelScope.launch {
            if (familiaRepository.checkIdPassEnFamilia(idFamilia, passFamilia)) {
                usuarioRepository.agregarFamiliaEnUsuario(prefsHelper.getUserId(), idFamilia)
                prefsHelper.saveFamilyId(idFamilia)
                authState.postValue(AuthState(loggedConFamilia = true))
            } else {
                authState.postValue(AuthState(errorMessage = "Datos incorrectos."))
            }
        }
    }

    fun familiaDataChanged(nombreFamilia: String, passFamilia: String) {
        if (isNombreValid(nombreFamilia) && isPasswordValid(passFamilia))
            authState.postValue(AuthState(isDataValid = true))
        else authState.postValue(AuthState(isDataValid = false))
    }

    fun loginDataChanged(email: String, pass: String) {
        if (isEmailValid(email) && isPasswordValid(pass))
            authState.postValue(AuthState(isDataValid = true))
        else authState.postValue(AuthState(isDataValid = false))
    }

    private fun isNombreValid(nombre: String): Boolean =
        nombre.length > 3

    private fun isEmailValid(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordValid(pass: String): Boolean =
        pass.length > 5

}
