package com.ort.listapp.domain.model

data class ItemLista(
    var producto: Producto = Producto(),
    var cantidad: Int = 0,
    val nombreUsuario: String = ""
)