//package com.ort.listapp.ui.lista_de_compras
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.ort.listapp.domain.model.Lista
//import com.ort.listapp.domain.model.ProductoListado
//import com.ort.listapp.domain.model.TipoLista
//
//class ListaDeComprasViewModel() : ViewModel() {
//
//    private val listaDeCompras: MutableLiveData<Lista> by lazy {
//        MutableLiveData<Lista>().also {
//            loadListaDeCompras(it)
//        }
//    }
//
//    fun getListaDeCompras(): LiveData<Lista> {
//        return listaDeCompras
//    }
//
//    private fun loadListaDeCompras(listaDeCompras: MutableLiveData<Lista>) {
//        listaDeCompras.value = Lista(
//            "listaDePrueba",
//            "Lista 1: Familia García",
//            "hoy",
//            TipoLista.LISTA_DE_COMPRAS,
//            mutableListOf(
//                ProductoListado(4, "Juan", "4058075498051"),
////            ProductoListado(2, "Candela", "7891000244111"),
////            ProductoListado(8, "Valentino", "0000075024956"),
////            ProductoListado(2, "Candela", "7790742656018"),
////            ProductoListado(1, "Rafael", "7790895007057"),
////            ProductoListado(4, "Valentino", "0080432400432"),
////            ProductoListado(3, "Valentino", "7790250047162"),
////            ProductoListado(1, "Martin", "0040000017318"),
////            ProductoListado(3, "Martin", "5410171921991"),
//            )
//        )
//    }
//
//    fun agregarProducto(idProducto: String, cantidad: Int, idUsuario: String) {
//        listaDeCompras.value?.agregarProducto(ProductoListado(cantidad, idUsuario, idProducto))
//    }
//
//    fun removerProducto(idProducto: String) {
//        val productoListado = buscarProductoListadoPorIdProducto(idProducto)
//        if (productoListado != null) {
//            listaDeCompras.value?.removerProducto(productoListado)
//        }
//    }
//
//    fun buscarProductoListadoPorIdProducto(id: String): ProductoListado? {
//        return listaDeCompras.value?.buscarProductoPorId(id)
//    }
//
//}