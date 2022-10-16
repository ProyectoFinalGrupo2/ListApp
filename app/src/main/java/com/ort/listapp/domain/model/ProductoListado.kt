package com.ort.listapp.domain.model

data class ProductoListado(
    val cantidad: Int,
    val usuarioId: String,
    val productoId: String,
) {
    constructor() : this(0, "", "")
}
