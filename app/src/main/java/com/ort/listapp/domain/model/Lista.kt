package com.ort.listapp.domain.model

data class Lista(
    val id: String,
    val nombre: String,
    val fechaCreacion: String,
    val tipoLista: TipoLista,
    val productos: List<ProductoListado>,
)

enum class TipoLista {
    LISTA_DE_COMPRAS,
    ALACENA_VIRTUAL,
    LISTA_FAVORITA,
    HISTORIAL
}