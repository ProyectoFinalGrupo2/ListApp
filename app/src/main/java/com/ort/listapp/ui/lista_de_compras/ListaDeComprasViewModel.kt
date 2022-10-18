package com.ort.listapp.ui.lista_de_compras

import androidx.lifecycle.ViewModel
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.ProductoListado
import com.ort.listapp.domain.model.TipoLista

class ListaDeComprasViewModel : ViewModel() {
    var listaDeCompras : Lista = Lista("listaDePrueba", "Lista 1: Familia García", "hoy", TipoLista.LISTA_DE_COMPRAS)

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

    fun cargarProds(){
        listaDeCompras.productos.forEach{
            agregarProducto(it.productoId, it.cantidad, it.usuarioId)
        }
    }
}