package com.ort.listapp.domain.model

data class Lista(
    val id: String,
    val nombre: String,
    val fechaCreacion: String,
    val tipoLista: TipoLista,
    val productos: List<ProductoListado>,
)

data class ProductoListado(
    val cantidad: Int,
    val usuarioId: String,
    val productoId: String,
)

enum class TipoLista {
    LISTA_DE_COMPRAS,
    ALACENA_VIRTUAL,
    LISTA_FAVORITA,
    HISTORIAL
}