package com.ort.listapp.domain.model

data class Familia(
    val id: String,
    val nombre: String,
    val password: String,
    val usuarios: List<String>,
    val productosPersonalizados: List<Producto>,
    val productosFavoritos: List<String>,
)
