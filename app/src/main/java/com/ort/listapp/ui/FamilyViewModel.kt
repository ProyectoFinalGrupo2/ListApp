package com.ort.listapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.listapp.ListaAppApplication.Companion.prefsHelper
import com.ort.listapp.data.FamiliaRepository
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.*
import com.ort.listapp.helpers.SysConstants
import kotlinx.coroutines.launch

class FamilyViewModel : ViewModel() {

    val repoProductos = ProductoRepository()
    val repoFamilia = FamiliaRepository()

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

    fun getProductosByTipoLista(tipoLista: TipoLista): List<ProductoListado> {
        return this.familia.value?.listas?.filter {
            it.tipoLista == tipoLista
        }?.get(0)?.productos ?: emptyList()
    }

    fun getProductosFavoritos(): List<Producto> {
        val listaIds = this.familia.value?.productosFavoritos!!
        return repoProductos.getProductosByListaIds(listaIds)
    }

    fun agregarProductoFavorito(idProducto: String) {
        val familia = this.familia.value
        familia?.productosFavoritos?.add(idProducto)
        actualizarFamilia(familia!!)
    }

    fun actualizarProductoPersonalizado(
        idProducto: String,
        nombre: String,
        precio: Double,
        id_categoria: String
    ) {
        val familia = this.familia.value
        val prod = familia?.productosPersonalizados?.find { it.id == idProducto }
        prod?.id = idProducto
        prod?.nombre = nombre
        prod?.precioMax = precio
        prod?.id_Categoria = id_categoria
        actualizarFamilia(familia!!)
    }

    fun eliminarProductoPersonalizado(producto: Producto) {
        // val prod = repoProductos.getProductoById(idProducto)

        val familia = this.familia.value
        viewModelScope.launch {
            familia?.productosPersonalizados?.remove(producto)
            eliminarProductoFavorito(producto.id)
            removerProductoDeLista(TipoLista.LISTA_DE_COMPRAS, producto.id)
            actualizarFamilia(familia!!)
        }
    }

    fun eliminarProductoFavorito(idProducto: String) {
        val familia = this.familia.value
        familia?.productosFavoritos?.remove(idProducto)
        actualizarFamilia(familia!!)
    }

    fun esProductoFav(idProducto: String): Boolean {
        var existe = false
        val familia = this.familia.value
        if (familia != null) {
            val prod = familia?.productosFavoritos?.find { it == idProducto }
            if (prod != null) {
                existe = true
            }
        }
        return existe
    }

    fun getProductosPersonalizados(): MutableList<Producto> {
        return this.familia.value?.productosPersonalizados?.toMutableList()!!
    }

    fun agregarProductoPersonalizado(nombre: String, precio: Double, id_categoria: String): String {
        val producto =
            Producto(
                id = "${SysConstants.PREFIJO_PROD_PERS}${System.currentTimeMillis()}",
                id_Categoria = id_categoria,
                nombre = nombre,
                precioMin = precio,
                precioMax = precio,
            )
        val familia = this.familia.value
        familia?.productosPersonalizados?.add(producto)
        actualizarFamilia(familia!!)
        return producto.id
    }

    fun agregarProductoEnLista(
        tipoLista: TipoLista,
        producto: Producto,
        cantidad: Int,
    ) {
        this.familia.value?.let { familia ->
            getListaByTipoEnFamilia(familia, tipoLista).agregarProducto(
                ProductoListado(
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
    }

    fun actualizarProductoEnLista(tipoLista: TipoLista, idProducto: String, cantidad: Int){
        this.familia.value?.let { familia ->
            getListaByTipoEnFamilia(familia, tipoLista).modificarCantidadPorId(
                idProducto, cantidad
            )
            actualizarFamilia(familia)
        }
    }

    fun removerProductoDeLista(
        tipoLista: TipoLista,
        idProducto: String,
    ) {
        this.familia.value?.let { familia ->
            getListaByTipoEnFamilia(familia, tipoLista).removerProductoPorId(idProducto)
            actualizarFamilia(familia!!)
        }
    }

    private fun getListaByTipoEnFamilia(familia: Familia, tipoLista: TipoLista): Lista {
        return familia.listas.filter {
            it.tipoLista == tipoLista
        }[0]
    }

    private fun actualizarFamilia(familia: Familia) {
//        this.familia.postValue(familia)
        viewModelScope.launch {
            repoFamilia.guardarFamilia(familia)
        }
    }
}

