package com.ort.listapp.domain.model

import java.util.Date

data class Lista(
    val id: Int,
    val nombre: String,
    val fechaCreacion: Date,
    val tipoLista: TipoLista,
    val productos: List<ProductoListado>,
)

data class ProductoListado(
    val cantidad: Int,
    val usuarioId: Int,
    val productoId: String,
)

enum class TipoLista {
    LISTA_DE_COMPRAS,
    ALACENA_VIRTUAL,
    LISTA_FAVORITA,
    HISTORIAL
}