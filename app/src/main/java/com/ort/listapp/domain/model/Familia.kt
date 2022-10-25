package com.ort.listapp.domain.model

data class Familia(
    var id: String = "",
    val nombre: String = "",
    val password: String = "",
    val usuarios: List<String> = listOf(),
    val productosPersonalizados: ArrayList<Producto> = arrayListOf(),
    var productosFavoritos: ArrayList<String> = arrayListOf(),
    val listas: ArrayList<Lista> = arrayListOf(),
)
