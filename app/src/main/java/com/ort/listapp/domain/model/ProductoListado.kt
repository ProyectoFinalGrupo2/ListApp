package com.ort.listapp.domain.model

data class ProductoListado(
    val id: String = "",
    val nombre: String = "",
    var id_Categoria: String = "",
    var cantidad: Int = 0,
    var precio: Double = 0.0,
    val nombreUsuario: String = ""
) {
    fun imgURL(): String = "https://imagenes.preciosclaros.gob.ar/productos/${id}.jpg"
}