package com.ort.listapp.data

import com.google.firebase.auth.FirebaseAuth
import com.ort.listapp.domain.model.AuthResponse
import com.ort.listapp.domain.model.Usuario
import com.ort.listapp.utils.HelperClass.logErrorMessage
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val usuarioRepository = UsuarioRepository()

    suspend fun createUserWithEmailAndPassword(
        nombre: String,
        email: String,
        pass: String
    ): AuthResponse {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, pass).await()
            val usuarioNuevo = Usuario(
                uid = authResult.user?.uid!!,
                nombre = nombre,
                email = email,
            )
            usuarioRepository.crearUsuario(usuarioNuevo)
            AuthResponse(usuario = usuarioNuevo)
        } catch (e: Exception) {
            logErrorMessage(e.message)
            AuthResponse(errorMessage = "Se produjo un error creando el usuario")
        }
    }

    suspend fun signInWithEmailAndPassword(
        email: String,
        pass: String
    ): AuthResponse {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, pass).await()
            val usuario = usuarioRepository.obtenerUsuario(authResult.user?.uid!!)
            AuthResponse(usuario = usuario)
        } catch (e: Exception) {
            logErrorMessage(e.message)
            AuthResponse(errorMessage = "Se produjo un error iniciando sesión ")
        }
    }

    fun checkIfUserIsAuthenticated(): Boolean = firebaseAuth.currentUser != null

    fun logout() = firebaseAuth.signOut()
}