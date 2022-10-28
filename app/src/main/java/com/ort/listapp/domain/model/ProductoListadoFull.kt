package com.ort.listapp.domain.model

data class ProductoListadoFull(
    var cantidad: Int,
    val usuarioId: String,
    val producto: Producto,
)
