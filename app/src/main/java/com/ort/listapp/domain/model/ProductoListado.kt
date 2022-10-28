package com.ort.listapp.domain.model

import com.ort.listapp.data.ProductoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

data class ProductoListado(
    var cantidad: Int,
    val usuarioId: String,
    val productoId: String,
) {
    constructor() : this(0, "", "")

    fun getProducto(): Producto = runBlocking(Dispatchers.IO) {
        ProductoRepository().getProductoById(productoId)
    }
}