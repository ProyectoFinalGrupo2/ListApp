package com.ort.listapp.ui.lista

import androidx.lifecycle.ViewModel
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.Producto
import com.ort.listapp.domain.model.ProductoListado
import com.ort.listapp.domain.model.TipoLista

class ListaViewModel : ViewModel() {

    private var listaDeCompras : Lista = Lista("listaDePrueba", "Lista 1: Familia García", "hoy", TipoLista.LISTA_DE_COMPRAS)

    fun agregarProducto(idProducto:String, cantidad:Int, idUsuario:String){
        listaDeCompras.agregarProducto(ProductoListado(cantidad, idUsuario, idProducto))
    }

    fun removerProducto(idProducto:String){
        val productoListado = buscarProductoListadoPorIdProducto(idProducto)
        if (productoListado != null) {
            listaDeCompras.removerProducto(productoListado)
        }
    }

    fun buscarProductoListadoPorIdProducto(id:String) : ProductoListado? {
        return listaDeCompras.buscarProductoPorId(id)
    }
}