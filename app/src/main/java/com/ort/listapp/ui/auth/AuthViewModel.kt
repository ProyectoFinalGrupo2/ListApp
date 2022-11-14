package com.ort.listapp.ui.auth

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.listapp.ListaAppApplication.Companion.prefsHelper
import com.ort.listapp.data.AuthRepository
import com.ort.listapp.data.FamiliaRepository
import com.ort.listapp.data.UsuarioRepository
import com.ort.listapp.domain.model.Usuario
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
                savePrefs(usuarioNuevo)
                authState.postValue(AuthState(loggedSinFamilia = true))
            } else {
                authState.postValue(AuthState(errorMessage = authResponse.errorMessage))
            }
        }
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            val authResponse = authRepository.signInWithEmailAndPassword(email, pass)
            if (authResponse.errorMessage.isBlank()) {
                val usuario = authResponse.usuario
                savePrefs(usuario)
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

    fun passwordReset(email: String) {
        viewModelScope.launch {
            val authResponse = authRepository.sendPasswordResetEmail(email)
            authState.postValue(AuthState(errorMessage = authResponse.errorMessage))
        }
    }

    fun changeEmail(email: String) {
        viewModelScope.launch {
            val authResponse = authRepository.updateEmail(email)
            authState.postValue(
                AuthState(
                    successMessage = authResponse.successMessage,
                    errorMessage = authResponse.errorMessage,
                )
            )
        }
    }

    fun checkIfUserIsAuthenticated(): Boolean = authRepository.checkIfUserIsAuthenticated()

    fun logout() {
        authRepository.logout()
        prefsHelper.wipe()
    }

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

    fun borrarseDeFamilia() {
        viewModelScope.launch {
            usuarioRepository.quitarFamiliaDeUsuario(prefsHelper.getUserId())
            authState.postValue(AuthState(successMessage = "Se ha quitado de la familia"))
        }
    }

    private fun isNombreValid(nombre: String): Boolean =
        nombre.length > 2

    private fun isEmailValid(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordValid(pass: String): Boolean =
        pass.length > 5

    private fun savePrefs(usuario: Usuario) {
        prefsHelper.saveUserName(usuario.nombre)
        prefsHelper.saveUserId(usuario.uid)
        prefsHelper.saveUserEmail(usuario.email)
    }
}
