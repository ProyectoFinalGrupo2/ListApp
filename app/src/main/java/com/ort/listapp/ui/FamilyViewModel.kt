package com.ort.listapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.listapp.data.FamiliaRepository
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
        this.familia.value?.productosFavoritos!!

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

    fun esProductoFav(producto: Producto): Boolean {
        return this.familia.value?.productosFavoritos?.find { it == producto } != null
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
        this.familia.value?.let { familia ->
            familia.productosPersonalizados.remove(producto)
            eliminarProductoFavorito(producto)
            //removerProductoDeLista(TipoLista.LISTA_DE_COMPRAS, producto.id)
            actualizarFamilia(familia)
        }
    }

    fun getProductosPersonalizados(): MutableList<Producto> {
        return this.familia.value?.productosPersonalizados?.toMutableList()!!
    }

    fun agregarProductoPersonalizado(producto: Producto) {
        this.familia.value?.let { familia ->
            familia.productosPersonalizados.add(producto)
            actualizarFamilia(familia)
        }
    }

    fun realizarCompra(){
        val familia = this.familia.value

        //paso los items de la lista de compras a la alacena virtual y creo la lista de tipo historial
        val listaDeCompras = getListaByIdEnFamilia(familia!!, getIdListaDeComprasActual())
        val alacenaVirtual = getListaByIdEnFamilia(familia!!, getIdAlacenaVirtual())
        val nuevoHistorial : Lista = Lista("pruebaHistorial", "Compra " + LocalDate.now().toString(), LocalDate.now().toString())
        for (item: ItemLista in listaDeCompras.productos){
            alacenaVirtual.agregarProducto(item)
            nuevoHistorial.agregarProducto(item)
        }

        //vacío la lista de compras
        listaDeCompras.vaciarLista()

        //actualizo la familia
        actualizarFamilia(familia!!)
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
            actualizarFamilia(familia)
        }
    }*/

    fun getProductosByTipoLista(tipoLista: TipoLista): List<ItemLista> {
        return this.familia.value?.listas?.filter {
            it.tipoLista == tipoLista
        }?.get(0)?.productos ?: emptyList()
    }

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

    private fun getListaByIdEnFamilia(familia: Familia, id: String): Lista {
        return familia.listas.filter {
            it.id == id
        }[0]
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

