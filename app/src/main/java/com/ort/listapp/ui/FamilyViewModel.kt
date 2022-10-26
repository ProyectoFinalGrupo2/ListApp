package com.ort.listapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.listapp.data.FamiliaRepository
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class FamilyViewModel : ViewModel() {

    val repoProductos = ProductoRepository()
    val repoFamilia = FamiliaRepository()

    private val familia: MutableLiveData<Familia> by lazy {
        MutableLiveData<Familia>().also {
            loadFamilia(it)
        }
    }

    fun loadFamilia(it: MutableLiveData<Familia>) {
        viewModelScope.launch {
            it.value = repoFamilia.getFamiliaById("familiaId")
        }
    }

    fun getFamilia(): LiveData<Familia> {
        return this.familia
    }

    fun getListaByTipo(tipoLista: TipoLista): Lista? {
        return this.familia.value?.listas?.filter {
            it.tipoLista == tipoLista
        }?.get(0)
    }

    fun getProductosFavoritos(): List<Producto> {
        val listaIds = this.familia.value?.productosFavoritos!!
        return runBlocking {
            withContext(Dispatchers.Default) {
                repoProductos.getProductosByListaIds(listaIds)
            }
        }
    }

    fun agregarProductoFavorito(idProducto: String) {
        val familia = this.familia.value
        familia?.productosFavoritos?.add(idProducto)
        actualizarFamilia(familia!!)
    }

    fun eliminarProductoFavorito(idProducto: String) {
        val familia = this.familia.value
        familia?.productosFavoritos?.remove(idProducto)
        actualizarFamilia(familia!!)
    }

    fun eliminarProductoPersonalizado(producto: Producto) {
        // val prod = repoProductos.getProductoById(idProducto)

        val familia = this.familia.value
        viewModelScope.launch {
            familia?.productosPersonalizados?.remove(producto)
            actualizarFamilia(familia!!)
        }

    }

    fun getProductosPersonalizados(): MutableList<Producto> {
        return this.familia.value?.productosPersonalizados?.toMutableList()!!
    }

    fun agregarProductoPersonalizado(nombre: String, precio: Double, id_categoria: String) :String {
        val producto =
            Producto("1234567", id_categoria, id_categoria, "", nombre, precio, precio, "")
        val familia = this.familia.value
        familia?.productosPersonalizados?.add(producto)
        actualizarFamilia(familia!!)
        return producto.id
    }

    fun actualizarProductoPersonalizado(idProducto: String,nombre: String, precio: Double, id_categoria: String){
        val familia = this.familia.value
        val prod = familia?.productosPersonalizados?.find { it.id == idProducto }
        prod?.id = idProducto
        prod?.nombre = nombre
        prod?.precioMax = precio
        prod?.id_Categoria = id_categoria
        actualizarFamilia(familia!!)
    }
    fun agregarProductoEnLista(
        tipoLista: TipoLista,
        idProducto: String,
        cantidad: Int,
        idUsuario: String
    ) {
        val familia = this.familia.value
        if (familia != null) {
            getListaByTipoEnFamilia(familia, tipoLista).agregarProducto(
                ProductoListado(
                    cantidad,
                    idUsuario,
                    idProducto
                )
            )
        }
        actualizarFamilia(familia!!)
    }

    fun removerProductoDeLista(
        tipoLista: TipoLista,
        idProducto: String,
    ) {
        val familia = this.familia.value
        if (familia != null) {
            getListaByTipoEnFamilia(familia, tipoLista).removerProductoPorId(idProducto)
        }
        actualizarFamilia(familia!!)
    }

    private fun getListaByTipoEnFamilia(familia: Familia, tipoLista: TipoLista): Lista {
        return familia.listas.filter {
            it.tipoLista == tipoLista
        }[0]
    }

    private fun actualizarFamilia(familia: Familia) {
        this.familia.postValue(familia)
        viewModelScope.launch {
            repoFamilia.guardarFamilia(familia)
        }
    }
}