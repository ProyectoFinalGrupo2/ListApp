package com.ort.listapp.ui

//import com.ort.listapp.helpers.SysConstants
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.ort.listapp.data.FamiliaRepository
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.*
import com.ort.listapp.utils.HelperClass.logErrorMessage
import com.ort.listapp.utils.SysConstants
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

class FamilyViewModel : ViewModel() {

    private val repoProductos = ProductoRepository()
    private val repoFamilia = FamiliaRepository()

    private val familia by lazy {
        MutableLiveData<Familia>().also {
            repoFamilia.suscribeFamilia(it)
            logErrorMessage("Familia Suscripta")
        }
    }

    private val listaDeComprasChecklist: MutableList<ItemListaChecklist> = mutableListOf()
    private var estaEnRealizarCompra: Boolean = false

    fun getFamilia(): LiveData<Familia> {
        return this.familia
    }

    fun cargarChecklist() {
        val listaProvisional: MutableList<ItemListaChecklist> = mutableListOf()
        listaProvisional.addAll(listaDeComprasChecklist)
        listaDeComprasChecklist.clear()
        this.familia.value?.let { familia ->
            val listaDeCompras = getListaByIdEnFamilia(familia, getIdListaDeComprasActual())
            for (item: ItemLista in listaDeCompras.productos) {
                val prodCheck = listaProvisional.find { it.producto.id == item.producto.id }
                if (prodCheck != null) {
                    if (prodCheck.estado) {
                        listaDeComprasChecklist.add(
                            ItemListaChecklist(
                                item.producto,
                                item.cantidad,
                                item.nombreUsuario,
                                true
                            )
                        )
                    } else {
                        listaDeComprasChecklist.add(
                            ItemListaChecklist(
                                item.producto,
                                item.cantidad,
                                item.nombreUsuario,
                                false
                            )
                        )
                    }
                } else {
                    listaDeComprasChecklist.add(
                        ItemListaChecklist(
                            item.producto,
                            item.cantidad,
                            item.nombreUsuario,
                            false
                        )
                    )
                }
            }
        }
    }

    fun getProductosChecklist(): MutableList<ItemListaChecklist> {
        return listaDeComprasChecklist
    }

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

    private fun actualizarProductoPersEnLista(
        idLista: String,
        idProducto: String,
        nombre: String,
        precio: Double,
        id_categoria: String
    ) {
        val lista = this.getFamilia().value?.let { this.getListaByIdEnFamilia(it, idLista) }
        if (lista?.productos?.find { itemLista -> itemLista.producto.id == idProducto } != null) {
            lista.modificarProductoPorID(idProducto, nombre, precio, id_categoria)
        }
    }

    fun actualizarProductoPersonalizado(
        idProducto: String, nombre: String, precio: Double, id_categoria: String
    ) {
        val familia = this.getFamilia().value
        if (familia != null) {
            //Actualizo el producto personalizado
            familia.productosPersonalizados.find { it.id == idProducto }?.let {
                it.nombre = nombre
                it.precio = precio
                it.id_Categoria = id_categoria
            }
            //Actualizo en productos favoritos
            familia.productosFavoritos.find { it.id == idProducto }?.let {
                it.nombre = nombre
                it.precio = precio
                it.id_Categoria = id_categoria
            }

            //Itero y actualizo en las compras favoritas
            this.getListasByTipoEnFamilia(familia, TipoLista.LISTA_FAVORITA).forEach {
                it.id?.let { it1 ->
                    actualizarProductoPersEnLista(
                        it1,
                        idProducto,
                        nombre,
                        precio,
                        id_categoria
                    )
                }
            }
            //Actualizo en lista de compras
            this.actualizarProductoPersEnLista(
                getIdListaDeComprasActual(),
                idProducto,
                nombre,
                precio,
                id_categoria
            )
            //Actualizo en la alacena
            this.actualizarProductoPersEnLista(
                getIdAlacenaVirtual(),
                idProducto,
                nombre,
                precio,
                id_categoria
            )
            actualizarFamilia(familia)
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
            val nuevoHistorial = Lista(
                id = "Hist${System.currentTimeMillis()}",
                nombre = "${LocalDate.now()}",
                tipoLista = TipoLista.HISTORIAL
            )
            for (item: ItemListaChecklist in listaDeComprasChecklist) {
                if (item.estado) {
                    alacenaVirtual.agregarProducto(
                        ItemLista(
                            item.producto,
                            item.cantidad,
                            item.nombreUsuario
                        )
                    )
                    nuevoHistorial.agregarProducto(
                        ItemLista(
                            item.producto,
                            item.cantidad,
                            item.nombreUsuario
                        )
                    )
                    listaDeCompras.removerProductoPorId(item.producto.id)
                }
            }

            //recargo la lista de checklist
            cargarChecklist()

            familia.listas.add(nuevoHistorial)

            //actualizo la familia
            actualizarFamilia(familia)
        }
    }

    fun estaEnRealizarCompra(): Boolean {
        return estaEnRealizarCompra
    }

    fun setEstaEnRealizarCompra(esta: Boolean) {
        estaEnRealizarCompra = esta
    }

    fun hayProductosCheckeados(): Boolean {
        var count = 0
        for (item: ItemListaChecklist in listaDeComprasChecklist) {
            if (item.estado) {
                count++
            }
        }
        return count > 0
    }

    fun clickChecklistProducto(idProducto: String) {
        val prod = listaDeComprasChecklist.find { it.producto.id == idProducto }
        if (prod != null) {
            prod.estado = !prod.estado
        }
    }

    private fun pasarProductosALista(produtos: MutableList<ItemLista>, idLista: String) {
        val listaDestino = this.familia.value?.let { getListaByIdEnFamilia(it, idLista) }
        if (listaDestino != null) {
            for (prod in produtos) {
                listaDestino.agregarProducto(prod)
            }
            this.familia.value?.let { actualizarFamilia(it) }

        }
    }

    fun copiarListaFavorita(idLista: String) {
        val listaACopiar = this.familia.value?.let { getListaByIdEnFamilia(it, idLista) }
        if (listaACopiar != null) {
            this.pasarProductosALista(listaACopiar.productos, this.getIdListaDeComprasActual())
        }
    }

    fun precioTotalListaById(id: String): Double {
        val familia = this.familia.value
        val lista = getListaByIdEnFamilia(familia!!, id)
        var precioTotal = 0.0
        for (item: ItemLista in lista.productos) {
            precioTotal += (item.producto.precio * item.cantidad)
        }
        return DecimalFormat("#.##").format(precioTotal).toDouble()
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

    private fun getListaByIdEnFamilia(familia: Familia, id: String): Lista =
        familia.listas.filter {
            it.id == id
        }[0]


    fun getListasByTipoEnFamilia(familia: Familia, tipoLista: TipoLista): MutableList<Lista> {
        return familia.listas.filter {
            it.tipoLista == tipoLista
        }.toMutableList()
    }

    fun crearLista(nombre: String, tipoLista: TipoLista) {

        this.familia.value?.let { familia ->
            val listaDeCompras = getListaByIdEnFamilia(familia, getIdListaDeComprasActual())
            if (listaDeCompras.productos.isNotEmpty()) {
                val nuevaLista = Lista(
                    "ListaFav${System.currentTimeMillis()}",
                    nombre,
                    tipoLista
                )
                for (prod in listaDeCompras.productos) {
                    nuevaLista.agregarProducto(prod)
                }
                familia.listas.add(nuevaLista)
                actualizarFamilia(familia)
            }
        }
    }

    fun borrarListaFavorita(idListaActual: String?) {
        val listaABorrar = idListaActual?.let {
            this.familia.value?.let { it1 ->
                getListaByIdEnFamilia(
                    it1, it
                )
            }
        }
        if (listaABorrar != null && listaABorrar.tipoLista!!.equals(TipoLista.LISTA_FAVORITA)) {
            this.familia.value?.listas?.remove(listaABorrar)
            this.familia.value?.let { this.actualizarFamilia(it) }
        }
    }

    fun hayProductosEnLista(): Boolean? {
        return this.familia.value?.listas?.find {
            it.tipoLista == TipoLista.LISTA_DE_COMPRAS
        }?.productos?.isNotEmpty()
    }


    private fun actualizarFamilia(familia: Familia) {
        viewModelScope.launch {
            repoFamilia.guardarFamilia(familia)
        }
    }

    fun actualizarPrecios() {
        this.familia.value?.let { familia ->
            if (hayQueActualizar()) {
                val listaProductos = obtenerProductosParaActualizarPrecios()
                if (listaProductos.isNotEmpty()) {
                    viewModelScope.launch {
                        val precios =
                            repoProductos.obtenerPrecios(listaProductos)
                        if (precios != null) {
                            actualizarPreciosEnListas(precios)
                            familia.ultimaActualizacionPrecios = Timestamp.now()
                            actualizarFamilia(familia)
                        }
                    }
                } else familia.ultimaActualizacionPrecios = Timestamp.now()
            }
        }
    }

    private fun hayQueActualizar(): Boolean {
        this.familia.value?.ultimaActualizacionPrecios.let {
            return it?.seconds!! < getHorarioDeCorte()
        }
    }

    private fun obtenerProductosParaActualizarPrecios(): List<String> {
        val set = mutableSetOf<String>()
        getProductosFavoritos().forEach {
            set.add(it.id)
        }
        this.familia.value?.listas?.forEach { lista ->
            if (lista.tipoLista != TipoLista.HISTORIAL) {
                lista.productos.forEach {
                    if (!it.producto.id.startsWith(SysConstants.PREFIJO_PROD_PERS)) {
                        set.add(it.producto.id)
                    }
                }
            }
        }
        return set.toList()
    }

    private fun actualizarPreciosEnListas(precios: Map<String, Double>) {
        this.familia.value?.let { familia ->
            familia.productosFavoritos = familia.productosFavoritos.map {
                if (precios.containsKey(it.id)) it.precio = precios[it.id]!!
                it
            } as ArrayList<Producto>
            familia.listas = familia.listas.map { lista ->
                if (lista.tipoLista != TipoLista.HISTORIAL) {
                    lista.productos = lista.productos.map { itemLista ->
                        val id = itemLista.producto.id
                        if (!id.startsWith(SysConstants.PREFIJO_PROD_PERS) && precios.containsKey(id)) {
                            itemLista.producto.precio = precios[id]!!
                        }
                        itemLista
                    } as ArrayList<ItemLista>
                }
                lista
            } as ArrayList<Lista>
        }
    }

    private fun getHorarioDeCorte(): Long {
        return LocalDateTime.of(
            LocalDate.now(), LocalTime.of(12, 0)
        ).toEpochSecond(ZoneOffset.ofHours(-3))
    }
}



