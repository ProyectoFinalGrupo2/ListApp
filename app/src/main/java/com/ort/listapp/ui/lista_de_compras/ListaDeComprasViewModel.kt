package com.ort.listapp.ui.lista_de_compras

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.ProductoListado
import com.ort.listapp.domain.model.TipoLista

class ListaDeComprasViewModel() : ViewModel() {
//    var listaDeCompras: Lista =
//        Lista("listaDePrueba", "Lista 1: Familia García", "hoy", TipoLista.LISTA_DE_COMPRAS)

    var listaDeCompras: MutableLiveData<Lista> = MutableLiveData<Lista>()

    //val listaDeCompras: LiveData<Lista> = _listaDeCompras

    init {
        listaDeCompras.value = Lista(
            "listaDePrueba",
            "Lista 1: Familia García",
            "hoy",
            TipoLista.LISTA_DE_COMPRAS,
            mutableListOf(
                ProductoListado(4, "Juan", "4058075498051"),
//            ProductoListado(2, "Candela", "7891000244111"),
//            ProductoListado(8, "Valentino", "0000075024956"),
//            ProductoListado(2, "Candela", "7790742656018"),
//            ProductoListado(1, "Rafael", "7790895007057"),
//            ProductoListado(4, "Valentino", "0080432400432"),
//            ProductoListado(3, "Valentino", "7790250047162"),
//            ProductoListado(1, "Martin", "0040000017318"),
//            ProductoListado(3, "Martin", "5410171921991"),
            )
        )
    }


    fun agregarProducto(idProducto: String, cantidad: Int, idUsuario: String) {

        listaDeCompras.value?.agregarProducto(ProductoListado(cantidad, idUsuario, idProducto))
       // _listaDeCompras.postValue(lista)
        val lista = Lista("listaDePrueba",
            "Lista 1: Familia García",
            "hoy",
            TipoLista.LISTA_DE_COMPRAS,
            listaDeCompras.value!!.productos

        )
        listaDeCompras.value = lista
        //listaDeCompras.postValue(lista)
    }

    fun removerProducto(idProducto: String) {
        val productoListado = buscarProductoListadoPorIdProducto(idProducto)
        if (productoListado != null) {
            listaDeCompras.value?.removerProducto(productoListado)
        }
    }

    fun buscarProductoListadoPorIdProducto(id: String): ProductoListado? {
        return listaDeCompras.value?.buscarProductoPorId(id)
    }

//    fun cargarProds() {
//        lista.agregarProducto(ProductoListado(4, "Juan", "4058075498051"))
//        lista.agregarProducto(ProductoListado(2, "Candela", "7891000244111"))
//        lista.agregarProducto(ProductoListado(8, "Valentino", "0000075024956"))
//        lista.agregarProducto(ProductoListado(2, "Candela", "7790742656018"))
//        lista.agregarProducto(ProductoListado(1, "Rafael", "7790895007057"))
//        lista.agregarProducto(ProductoListado(4, "Valentino", "0080432400432"))
//        lista.agregarProducto(ProductoListado(3, "Valentino", "7790250047162"))
//        lista.agregarProducto(ProductoListado(1, "Martin", "0040000017318"))
//        lista.agregarProducto(ProductoListado(3, "Martin", "5410171921991"))
//        listaDeCompras.postValue(lista)
//    }
}