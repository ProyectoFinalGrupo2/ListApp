package com.ort.listapp.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.listapp.domain.model.Producto
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class ProductoRepository {

    val db = Firebase.firestore
    val productosRef = db.collection("productos")

    suspend fun getProductosByListaIds(lista: List<String>): List<Producto> =
        coroutineScope {
            lista.map { productoId ->
                async {
                    getProductoById(productoId)
                }
            }.awaitAll()
        }

    private suspend fun getProductoById(productoId: String): Producto {
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