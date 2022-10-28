package com.ort.listapp.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.listapp.domain.model.Producto
import com.ort.listapp.helpers.SysConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class ProductoRepository {

    private val db = Firebase.firestore
    private val productosRef = db.collection("productos")

    fun getProductosByListaIds(lista: List<String>): List<Producto> =
        runBlocking(Dispatchers.IO) {
            lista.map { productoId ->
                getProductoById(productoId)
            }
        }

    suspend fun getProductoById(productoId: String): Producto {
        var producto = Producto(id = productoId, nombre = "No encontrado")
        if (productoId.startsWith(SysConstants.PREFIJO_PROD_PERS)) {

        } else {
            val documentSnapshot = productosRef.document(productoId).get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject<Producto>()?.let {
                    producto = it
                    producto.id = productoId
                }
            }
        }
        return producto
    }

}

