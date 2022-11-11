package com.ort.listapp.domain.model

class ItemListaChecklist (
    var producto: Producto = Producto(),
    var cantidad: Int = 0,
    val nombreUsuario: String = "",
    var estado: Boolean
)