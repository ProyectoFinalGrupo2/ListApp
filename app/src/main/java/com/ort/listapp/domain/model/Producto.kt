package com.ort.listapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Producto(
    @SerialName("objectID") var id: String = "",
    var id_Categoria: String = "",
    var id_subCategoria: String = "",
    var marca: String = "",
    var nombre: String = "",
    @SerialName("precioMax") var precio: Double = 0.0,
    var presentacion: String = "",
) {
    fun imgURL(): String = "https://imagenes.preciosclaros.gob.ar/productos/${id}.jpg"
}
