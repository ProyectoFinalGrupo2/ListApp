package com.ort.listapp.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.listapp.domain.model.Usuario
import com.ort.listapp.utils.SysConstants.DB_USUARIOS
import kotlinx.coroutines.tasks.await

class UsuarioRepository {

    private val db = Firebase.firestore
    private val usuariosRef = db.collection(DB_USUARIOS)

    suspend fun crearUsuario(usuario: Usuario) {
        usuariosRef.document(usuario.uid).set(usuario).await()
    }

    suspend fun obtenerUsuario(uid: String): Usuario {
        val documentSnapshot: DocumentSnapshot = usuariosRef.document(uid).get().await()
        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject<Usuario>()!!
        } else {
            throw Exception("Error en obtenerUsuario()")
        }
    }

    suspend fun agregarFamiliaEnUsuario(uid: String, familiaId: String) {
        usuariosRef.document(uid).update("familia", familiaId).await()
    }
}