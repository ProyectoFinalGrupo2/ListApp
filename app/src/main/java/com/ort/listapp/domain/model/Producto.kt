package com.ort.listapp.domain.model

data class Producto(
    var id: String,
    var id_Categoria: String,
    var id_subCategoria: String,
    var marca: String,
    var nombre: String,
    var precioMin: Double,
    var precioMax: Double,
    var presentacion: String,
) {
    constructor() : this("", "", "", "", "", 0.0, 0.0, "")

    fun imgURL(): String = "https://imagenes.preciosclaros.gob.ar/productos/${id}.jpg"
}
