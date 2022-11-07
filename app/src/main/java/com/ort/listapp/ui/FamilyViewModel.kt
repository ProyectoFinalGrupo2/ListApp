package com.ort.listapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.listapp.data.FamiliaRepository
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.*
import com.ort.listapp.helpers.SysConstants
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.DecimalFormat
import java.time.LocalDate

class FamilyViewModel : ViewModel() {

    private val repoProductos = ProductoRepository()
    private val repoFamilia = FamiliaRepository()

    private val familia by lazy {
        MutableLiveData<Familia>().also {
//            loadFamilia(it)
            repoFamilia.suscribeFamilia(it)
        }
    }

//    fun loadFamilia(it: MutableLiveData<Familia>) {
//        viewModelScope.launch {
//            try {
//                it.value = repoFamilia.getFamiliaById("familiaId")
//            } catch (e: Exception) {
//                Log.w(TAG, "Error getting documents: ", e)
//            }
//        }
//    }


    fun getFamilia(): LiveData<Familia> {
        return this.familia
    }

    /*fun getProductosByTipoLista(tipoLista: TipoLista): List<ItemLista> {
        return this.familia.value?.listas?.filter {
            it.tipoLista == tipoLista
        }?.get(0)?.productos ?: emptyList()
    }*/

    fun getProductosByIdLista(idLista: String): List<ItemLista> {
        return this.familia.value?.listas?.filter {
            it.id == idLista
        }?.get(0)?.productos ?: emptyList()
    }

    fun getProductosFavoritos(): List<Producto> =
        this.familia.value?.productosFavoritos ?: emptyList()


    fun agregarProductoFavorito(producto: Producto) {
        this.familia.value?.let { familia ->
            familia.productosFavoritos.add(producto)
            actualizarFamilia(familia)
        }
    }

    fun eliminarProductoFavorito(producto: Producto) {
        this.familia.value?.let { familia ->
            familia.productosFavoritos.remove(producto)
            actualizarFamilia(familia)
        }
    }

    fun esProductoFav(producto: Producto): Boolean =
        this.familia.value?.productosFavoritos?.find { it == producto } != null


    fun actualizarProductoPersonalizado(
        idProducto: String,
        nombre: String,
        precio: Double,
        id_categoria: String
    ) {
        this.familia.value?.let { familia ->
            val producto = familia.productosPersonalizados.find { it.id == idProducto }
            producto?.let {
                it.id = idProducto
                it.nombre = nombre
                it.precio = precio
                it.id_Categoria = id_categoria
                actualizarFamilia(familia)
            }
        }
    }

    fun eliminarProductoPersonalizado(producto: Producto) {
        this.familia.value?.let { familia ->
            familia.productosPersonalizados.remove(producto)
            eliminarProductoFavorito(producto)
            //removerProductoDeLista(TipoLista.LISTA_DE_COMPRAS, producto.id)
            actualizarFamilia(familia)
        }
    }

    fun getProductosPersonalizados(): MutableList<Producto> =
        this.familia.value?.productosPersonalizados?.toMutableList()!!


    fun agregarProductoPersonalizado(producto: Producto) {
        this.familia.value?.let { familia ->
            familia.productosPersonalizados.add(producto)
            actualizarFamilia(familia)
        }
    }

    fun realizarCompra() {
        this.familia.value?.let { familia ->

            //paso los items de la lista de compras a la alacena virtual y creo la lista de tipo historial
            val listaDeCompras = getListaByIdEnFamilia(familia, getIdListaDeComprasActual())
            val alacenaVirtual = getListaByIdEnFamilia(familia, getIdAlacenaVirtual())
            val nuevoHistorial: Lista = Lista(
                "pruebaHistorial",
                "Compra " + LocalDate.now().toString(),
                LocalDate.now().toString()
            )
            for (item: ItemLista in listaDeCompras.productos) {
                alacenaVirtual.agregarProducto(item)
                nuevoHistorial.agregarProducto(item)
            }

            //vacío la lista de compras
            listaDeCompras.vaciarLista()

            //actualizo la familia
            //actualizo la familia
            actualizarFamilia(familia!!)
        }
    }

    fun precioTotalListaById(id: String): Double{
        val familia = this.familia.value
        val lista = getListaByIdEnFamilia(familia!!, id)
        var precioTotal : Double = 0.0
        for (item: ItemLista in lista.productos){
            precioTotal+=(item.producto.precio * item.cantidad)
        }
        return DecimalFormat("#.##").format(precioTotal).toDouble()
    }

    /*fun agregarProductoEnLista(
        tipoLista: TipoLista,
        producto: Producto,
        cantidad: Int,
    ) {
        this.familia.value?.let { familia ->
            getListaByTipoEnFamilia(familia, tipoLista).agregarProducto(
                ItemLista(
                    id = producto.id,
                    nombre = producto.nombre,
                    id_Categoria = producto.id_Categoria,
                    cantidad = cantidad,
                    precio = producto.precioMax,
                    nombreUsuario = prefsHelper.getUserName(),
                )
            )
            actualizarFamilia(familia)
        }
    }*/


    fun getProductosByTipoLista(tipoLista: TipoLista): List<ItemLista> =
        this.familia.value?.listas?.filter {
            it.tipoLista == tipoLista
        }?.get(0)?.productos ?: emptyList()


    fun agregarProductoEnListaById(
        idLista: String,
        item: ItemLista
    ) {
        this.familia.value?.let { familia ->
            getListaByIdEnFamilia(familia, idLista).agregarProducto(item)
            actualizarFamilia(familia)
        }
    }

    fun actualizarProductoEnListaById(idLista: String, idProducto: String, cantidad: Int) {
        this.familia.value?.let { familia ->
            getListaByIdEnFamilia(familia, idLista).modificarCantidadPorId(
                idProducto, cantidad
            )
            actualizarFamilia(familia)
        }
    }

    fun removerProductoDeListaById(
        idLista: String,
        idProducto: String,
    ) {
        this.familia.value?.let { familia ->
            getListaByIdEnFamilia(familia, idLista).removerProductoPorId(idProducto)
            actualizarFamilia(familia)
        }
    }

    //devuelve el id de la lista de compras, si no lo encuentra devuelve string vacío ""
    fun getIdListaDeComprasActual(): String {
        val idListaDeCompras: String? =
            this.familia.value?.listas?.find { it.tipoLista == TipoLista.LISTA_DE_COMPRAS }?.id
        return idListaDeCompras ?: ""
    }

    //devuelve el id de la alacena virtual, si no lo encuentra devuelve string vacío ""
    fun getIdAlacenaVirtual(): String {
        val idAlacenaVirtual: String? =
            this.familia.value?.listas?.find { it.tipoLista == TipoLista.ALACENA_VIRTUAL }?.id
        return idAlacenaVirtual ?: ""
    }

    private fun getListaByIdEnFamilia(familia: Familia, id: String): Lista =
        familia.listas.filter {
            it.id == id
        }[0]


    fun getListaByTipoEnFamilia(familia: Familia, tipoLista: TipoLista): List<Lista> {
        return familia.listas.filter {
            it.tipoLista == tipoLista
        }
    }

    fun crearListaFavorita(nombre:String){
        this.familia.value?.let { familia ->
            val listaDeCompras = getListaByIdEnFamilia(familia, getIdListaDeComprasActual())
            val nuevaLista = Lista(
                "ListaFav${System.currentTimeMillis()}",
                nombre,
                LocalDate.now().toString(),
                TipoLista.LISTA_FAVORITA
            )
            for (prod in listaDeCompras.productos){
                nuevaLista.agregarProducto(prod)
            }
            familia.listas.add(nuevaLista)
            actualizarFamilia(familia)
        }
    }

    private fun actualizarFamilia(familia: Familia) {
//        this.familia.postValue(familia)
        viewModelScope.launch {
            repoFamilia.guardarFamilia(familia)
        }
    }
}


