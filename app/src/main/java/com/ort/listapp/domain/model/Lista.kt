package com.ort.listapp.domain.model

import com.google.firebase.Timestamp

data class Lista(
    val id: String? = null,
    val nombre: String? = null,
    val tipoLista: TipoLista? = null,
    val fechaCreacion: Timestamp? = Timestamp.now(),
    val productos: MutableList<ItemLista> = mutableListOf(),
) {
    fun agregarProducto(itemLista: ItemLista) {
        val id = itemLista.producto.id
        if (productoEstaEnLista(id)) {
            modificarCantidadPorId(id, itemLista.cantidad)
        } else {
            productos.add(itemLista)
        }
    }

    fun removerProductoPorId(id: String) {
        productos.remove(productos.find { it.producto.id == id })
    }

    private fun buscarProductoPorId(id: String): ItemLista? {
        return productos.find { it.producto.id == id }
    }

    fun modificarCantidadPorId(id: String, cantidad: Int) {
        val prod = buscarProductoPorId(id)
        if (prod != null && prod.cantidad + cantidad > 0) {
            prod.cantidad += cantidad
        } else {
            removerProductoPorId(id)
        }
    }

    private fun productoEstaEnLista(id: String): Boolean {
        return productos.firstOrNull { it.producto.id == id } != null
    }

    fun vaciarLista() {
        productos.clear()
    }
}

