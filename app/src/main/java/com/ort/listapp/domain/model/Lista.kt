package com.ort.listapp.domain.model

data class Lista(
    val id: String,
    val nombre: String,
    val fechaCreacion: String,
    val tipoLista: TipoLista,
    val productos: MutableList<ProductoListado> = ArrayList<ProductoListado>(),
) {

    fun agregarProducto(prod: ProductoListado) {
        productos.add(prod)
        //acá debe llamar al repositorio para agregar el productos en la lista en la base de datos
    }

    fun removerProducto(prod: ProductoListado) {
        productos.remove(prod)
        //acá debe llamar al repositorio para remover el productos de la lista en la base de datos
    }

    fun removerProductoPorId(id: String) {
        productos.remove(productos.find { it.productoId == id })
        //acá debe llamar al repositorio para remover el productos de la lista en la base de datos
    }

    fun buscarProductoPorId(id: String): ProductoListado? {
        return productos.find { it.productoId == id };
    }

    fun getProds(): MutableList<ProductoListado> {
        return productos
    }
}

enum class TipoLista {
    LISTA_DE_COMPRAS,
    ALACENA_VIRTUAL,
    LISTA_FAVORITA,
    HISTORIAL
}