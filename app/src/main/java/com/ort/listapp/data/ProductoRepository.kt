package com.ort.listapp.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.listapp.domain.model.Producto
import kotlinx.coroutines.tasks.await

class ProductoRepository {

    private val db = Firebase.firestore
    private val productosRef = db.collection("productos")

    suspend fun getProductoById(productoId: String): Producto {
        var producto = Producto(id = productoId, nombre = "No encontrado")
        val documentSnapshot = productosRef.document(productoId).get().await()
        if (documentSnapshot.exists()) {
            documentSnapshot.toObject<Producto>()?.let {
                producto = it
                producto.id = productoId
            }
        }
        return producto
    }

}

