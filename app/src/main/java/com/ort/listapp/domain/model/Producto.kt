package com.ort.listapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Producto(

    @SerialName("objectID")
    var id: String = "",

    @SerialName("id_Categoria")
    var id_Categoria: String = "",

    @SerialName("nombre")
    var nombre: String = "",

    @SerialName("precioMax")
    var precio: Double = 0.0,

    ) {
    fun imgURL(): String = "https://imagenes.preciosclaros.gob.ar/productos/${id}.jpg"
}


//    var id_subCategoria: String = "",
//    var marca: String = "",
//    var presentacion: String = "",
