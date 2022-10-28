package com.ort.listapp.domain.model

data class Lista(
    val id: String? = null,
    val nombre: String? = null,
    val fechaCreacion: String? = null,
    val tipoLista: TipoLista? = null,
    val productos: MutableList<ProductoListado> = mutableListOf(),
) {

    fun agregarProducto(producto: ProductoListado) {
        if (productoEstaEnLista(producto.id)) {
            modificarCantidadPorId(producto.id, producto.cantidad)
        } else {
            productos.add(producto)
        }
    }

    fun removerProductoPorId(id: String) {
        productos.remove(productos.find { it.id == id })
    }

    private fun buscarProductoPorId(id: String): ProductoListado? {
        return productos.find { it.id == id };
    }

    private fun modificarCantidadPorId(id: String, cantidad: Int) {
        val prod = buscarProductoPorId(id)
        if (prod != null && prod.cantidad + cantidad > 0) {
            prod.cantidad += cantidad
        } else {
            removerProductoPorId(id)
        }
    }

    private fun productoEstaEnLista(id: String): Boolean {
        return productos.firstOrNull { it.id == id } != null
    }
}

