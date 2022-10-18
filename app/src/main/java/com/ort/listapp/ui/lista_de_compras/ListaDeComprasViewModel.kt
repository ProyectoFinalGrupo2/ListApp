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
        listaDeCompras.agregarProducto(ProductoListado(4, "Juan", "4058075498051"))
        listaDeCompras.agregarProducto(ProductoListado(2, "Candela", "7891000244111"))
        listaDeCompras.agregarProducto(ProductoListado(8, "Valentino", "0000075024956"))
        listaDeCompras.agregarProducto(ProductoListado(2, "Candela", "7790742656018"))
        listaDeCompras.agregarProducto(ProductoListado(1, "Rafael", "7790895007057"))
        listaDeCompras.agregarProducto(ProductoListado(4, "Valentino", "0080432400432"))
        listaDeCompras.agregarProducto(ProductoListado(3, "Valentino", "7790250047162"))
        listaDeCompras.agregarProducto(ProductoListado(1, "Martin", "0040000017318"))
        listaDeCompras.agregarProducto(ProductoListado(3, "Martin", "5410171921991"))
    }
}