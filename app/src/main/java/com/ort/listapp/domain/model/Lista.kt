package com.ort.listapp.domain.model

data class Lista(
    val id: String? = null,
    val nombre: String? = null,
    val fechaCreacion: String? = null,
    val tipoLista: TipoLista? = null,
    val productos: MutableList<ProductoListado> = mutableListOf(),
) {

    fun agregarProducto(prod: ProductoListado) {
        productos.add(prod)
    }

    fun removerProducto(prod: ProductoListado) {
        productos.remove(prod)
    }

    fun removerProductoPorId(id: String) {
        productos.remove(productos.find { it.productoId == id })
    }

    fun buscarProductoPorId(id: String): ProductoListado? {
        return productos.find { it.productoId == id };
    }
}

enum class TipoLista {
    LISTA_DE_COMPRAS,
    ALACENA_VIRTUAL,
    LISTA_FAVORITA,
    HISTORIAL
}