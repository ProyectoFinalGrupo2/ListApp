package com.ort.listapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class FamilyViewModel : ViewModel() {

    val repositorio = ProductoRepository()

    private val familia: MutableLiveData<Familia> by lazy {
        MutableLiveData<Familia>().also {
            loadFamilia(it)
        }
    }
//    val listaDeCompras = MutableLiveData<MutableList<Lista>>().apply {
//        value = familia.value?.listas
//            ?.filter { it.tipoLista == TipoLista.LISTA_DE_COMPRAS }
//            ?.toMutableList()
//    }

    private fun loadFamilia(it: MutableLiveData<Familia>) {
        it.value = Familia(
            "familiaId",
            "Los Argento",
            listas = arrayListOf(
                Lista(
                    "listaDePrueba",
                    "Lista 1: Familia García",
                    "hoy",
                    TipoLista.LISTA_DE_COMPRAS,
                    mutableListOf(
                        ProductoListado(4, "Juan", "4058075498051"),
//                        ProductoListado(2, "Candela", "7891000244111"),
//            ProductoListado(8, "Valentino", "0000075024956"),
//            ProductoListado(2, "Candela", "7790742656018"),
//            ProductoListado(1, "Rafael", "7790895007057"),
//            ProductoListado(4, "Valentino", "0080432400432"),
//            ProductoListado(3, "Valentino", "7790250047162"),
//            ProductoListado(1, "Martin", "0040000017318"),
//            ProductoListado(3, "Martin", "5410171921991"),
                    )
                )
            ),
            productosFavoritos = arrayListOf(
                "0080432400432",
//                "7790895007057",
//                "7790742656018",
//                "0000077900319"
            ),
            productosPersonalizados = arrayListOf(
                Producto(
                    "5410171921991",
                    "01",
                    "0108",
                    "MC CAIN",
                    "Croquetas de Papas Noisettes Mc Cain 1 Kg",
                    978.0,
                    997.0,
                    "1.0 kg"
                ),
                Producto(
                    "0040000017318",
                    "02",
                    "0208",
                    "M&M",
                    "Confites de Chocolate M&M 150 Gr",
                    1010.0,
                    1047.99,
                    "150.0 gr"
                ),
                Producto(
                    "7790250047162",
                    "03",
                    "0302",
                    "BABYSEC",
                    "Pañal G Babysec Premium 1 U",
                    865.65,
                    865.65,
                    "1.0 un"
                ),
                Producto(
                    "7891000244111",
                    "08",
                    "0801",
                    "DOG CHOW",
                    "Alimento para Perros Humedo Razas Pequeñas Pollo Dog Chow 100 Gr",
                    172.0,
                    196.0,
                    "100.0 gr"
                )
            )
        )
    }


    fun getFamilia(): LiveData<Familia> {
        return this.familia
    }

    fun getListaByTipo(tipoLista: TipoLista): Lista? {
        return this.familia.value?.listas?.filter {
            it.tipoLista == tipoLista
        }?.get(0)
    }

    fun getProductosFavoritos(): MutableList<Producto> {
        val listaIds = this.familia.value?.productosFavoritos!!
        var productos = mutableListOf<Producto>()
        runBlocking {
            val resp = async { repositorio.getProductosByListaIds(listaIds) }
            runBlocking {
                productos = resp.await()
            }
        }
        return productos
    }

    fun agregarProductoFavorito(idProducto: String) {
        val familia = this.familia.value
        familia?.productosFavoritos?.add(idProducto)
        actualizarFamilia(familia)
    }

    fun eliminarProductoFavorito(idProducto: String) {
        val familia = this.familia.value
        familia?.productosFavoritos?.remove(idProducto)
        actualizarFamilia(familia)
    }

    fun getProductosPersonalizados(): MutableList<Producto> {
        return this.familia.value?.productosPersonalizados?.toMutableList()!!
    }
    fun agregarProductoPersonalizado(nombre: String,precio:Double,id_categoria:String) {
        val producto = Producto("1234567",id_categoria,id_categoria,"",nombre,precio,precio,"")
        val familia = this.familia.value
        familia?.productosPersonalizados?.add(producto)
        actualizarFamilia(familia)
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
        actualizarFamilia(familia)
    }

    fun removerProductoDeLista(
        tipoLista: TipoLista,
        idProducto: String,
    ) {
        val familia = this.familia.value
        if (familia != null) {
            getListaByTipoEnFamilia(familia, tipoLista).removerProductoPorId(idProducto)
        }
        actualizarFamilia(familia)
    }

    private fun getListaByTipoEnFamilia(familia: Familia, tipoLista: TipoLista): Lista {
        return familia.listas.filter {
            it.tipoLista == tipoLista
        }[0]
    }

    private fun actualizarFamilia(familia: Familia?) {
        this.familia.postValue(familia)
        // Aca hay que actualizar la DB
    }
}