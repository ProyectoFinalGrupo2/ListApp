package com.ort.listapp.domain.model

data class Lista(
    val id: String? = null,
    val nombre: String? = null,
    val fechaCreacion: String? = null,
    val tipoLista: TipoLista? = null,
    val productos: MutableList<ProductoListado> = mutableListOf(),
) {

    fun agregarProducto(prod: ProductoListado) {
        if(productoEstaEnLista(prod.productoId)){
            modificarCantidadPorId(prod.productoId, prod.cantidad)
        }else{
            productos.add(prod)
        }
    }

    fun removerProductoPorId(id: String) {
        productos.remove(productos.find { it.productoId == id })
    }

    fun buscarProductoPorId(id: String): ProductoListado? {
        return productos.find { it.productoId == id };
    }

    fun modificarCantidadPorId(id: String, cantidad: Int){
        var prod = buscarProductoPorId(id)
        if(prod != null && prod.cantidad + cantidad > 0){
            prod.cantidad += cantidad
        }else{
            removerProductoPorId(id)
        }
    }

    fun productoEstaEnLista(id: String): Boolean{
        return productos.firstOrNull{it.productoId == id} != null
    }
}

