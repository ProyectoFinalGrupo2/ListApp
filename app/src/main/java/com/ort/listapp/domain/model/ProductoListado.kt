package com.ort.listapp.domain.model

data class ProductoListado(
    var cantidad: Int,
    val usuarioId: String,
    val productoId: String,
) {
    constructor() : this(0, "", "")
}
