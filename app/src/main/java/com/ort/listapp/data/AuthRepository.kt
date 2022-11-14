package com.ort.listapp.data

import com.google.firebase.auth.FirebaseAuth
import com.ort.listapp.domain.model.AuthResponse
import com.ort.listapp.domain.model.Usuario
import com.ort.listapp.utils.HelperClass.logErrorMessage
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
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
            usuario.email = email
            AuthResponse(usuario = usuario)
        } catch (e: Exception) {
            logErrorMessage(e.message)
            AuthResponse(errorMessage = "Se produjo un error iniciando sesión ")
        }
    }

    suspend fun sendPasswordResetEmail(
        email: String,
    ): AuthResponse {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            AuthResponse(errorMessage = "Email enviado")
        } catch (e: Exception) {
            logErrorMessage(e.message)
            AuthResponse(errorMessage = "Se produjo un error restableciendo contraseña")
        }
    }

    suspend fun updateEmail(email: String): AuthResponse {
        return try {
            firebaseAuth.currentUser!!.updateEmail(email).await()
            AuthResponse(successMessage = "Email modificado")
        } catch (e: Exception) {
            logErrorMessage(e.message)
            AuthResponse(errorMessage = "Se produjo un error cambiando el email")
        }
    }

    suspend fun userDelete(): AuthResponse {
        return try {
            firebaseAuth.currentUser!!.delete().await()
            AuthResponse(errorMessage = "Usuario eliminado")
        } catch (e: Exception) {
            logErrorMessage(e.message)
            AuthResponse(errorMessage = "Se produjo un error borrando el usuario")
        }
    }

    fun checkIfUserIsAuthenticated(): Boolean = firebaseAuth.currentUser != null

    fun logout() = firebaseAuth.signOut()
}