package com.ort.listapp.ui

//import com.ort.listapp.helpers.SysConstants
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.listapp.data.FamiliaRepository
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.*
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime

class FamilyViewModel : ViewModel() {

    private val repoProductos = ProductoRepository()
    private val repoFamilia = FamiliaRepository()

    private val familia by lazy {
        MutableLiveData<Familia>().also {
//            loadFamilia(it)
            repoFamilia.suscribeFamilia(it)
        }
    }

    private val listaDeComprasChecklist: MutableList<ItemListaChecklist> = mutableListOf()

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

    fun cargarChecklist(){
        listaDeComprasChecklist.clear()
        this.familia.value?.let { familia ->
            val listaDeCompras = getListaByIdEnFamilia(familia, getIdListaDeComprasActual())
            for (item: ItemLista in listaDeCompras.productos) {
                listaDeComprasChecklist.add(ItemListaChecklist(item.producto, item.cantidad, item.nombreUsuario, false))
            }
        }
    }

    fun getProductosChecklist(): MutableList<ItemListaChecklist>{
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
                id = "Hist${System.currentTimeMillis()}",
                nombre = "Compra " + LocalDate.now().toString(),
                tipoLista = TipoLista.HISTORIAL
            )
            for (item: ItemListaChecklist in listaDeComprasChecklist) {
                if(item.estado){
                    alacenaVirtual.agregarProducto(ItemLista(item.producto, item.cantidad, item.nombreUsuario))
                    nuevoHistorial.agregarProducto(ItemLista(item.producto, item.cantidad, item.nombreUsuario))
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

    fun clickChecklistProducto(idProducto: String){
        val prod = listaDeComprasChecklist.find { it.producto.id == idProducto }
        if (prod != null) {
            prod.estado = !prod.estado
        }
    }

    fun vaciarCheckList(){
        listaDeComprasChecklist.clear()
    }

    private fun pasarProductosALista(produtos:MutableList<ItemLista>,idLista: String){
        val listaDestino = this.familia.value?.let { getListaByIdEnFamilia(it,idLista) }
        if(listaDestino!=null){
            for(prod in produtos){
                listaDestino.agregarProducto(prod)
            }
            this.familia.value?.let { actualizarFamilia(it) }

        }
    }

    fun copiarListaFavorita(idLista: String){
        val listaACopiar = this.familia.value?.let { getListaByIdEnFamilia(it,idLista) }
        if(listaACopiar != null){
            this.pasarProductosALista(listaACopiar.productos,this.getIdListaDeComprasActual())
        }
    }

    fun precioTotalListaById(id: String): Double {
        val familia = this.familia.value
        val lista = getListaByIdEnFamilia(familia!!, id)
        var precioTotal: Double = 0.0
        for (item: ItemLista in lista.productos) {
            precioTotal += (item.producto.precio * item.cantidad)
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


    fun getListasByTipoEnFamilia(familia: Familia, tipoLista: TipoLista): List<Lista> {
        return familia.listas.filter {
            it.tipoLista == tipoLista
        }
    }

    fun crearListaFavorita(nombre: String) {
        this.familia.value?.let { familia ->
            val listaDeCompras = getListaByIdEnFamilia(familia, getIdListaDeComprasActual())
            val nuevaLista = Lista(
                "ListaFav${System.currentTimeMillis()}",
                nombre,
                TipoLista.LISTA_FAVORITA
            )
            for (prod in listaDeCompras.productos) {
                nuevaLista.agregarProducto(prod)
            }
            familia.listas.add(nuevaLista)
            actualizarFamilia(familia)
        }
    }
    fun borrarListaFavorita(idListaActual: String?) {
    val listaABorrar = idListaActual?.let { this.familia.value?.let { it1 ->
        getListaByIdEnFamilia(
            it1, it)
    }}
        if(listaABorrar!= null && listaABorrar.tipoLista!!.equals(TipoLista.LISTA_FAVORITA) ){
            this.familia.value?.listas?.remove(listaABorrar)
            this.familia.value?.let { this.actualizarFamilia(it) }
        }
    }

/*    fun crearListaFavorita(nombre:String, tipoLista: TipoLista){
        this.familia.value?.let { familia ->
            val listaDeCompras = getListaByIdEnFamilia(familia, getIdListaDeComprasActual())
            val nuevaLista = Lista(
                "ListaFav${System.currentTimeMillis()}",
                nombre,
                Timestamp.now(),
                tipoLista
            )
            for (prod in listaDeCompras.productos){
                nuevaLista.agregarProducto(prod)
            }
            familia.listas.add(nuevaLista)
            actualizarFamilia(familia)
        }
    }*/

    private fun actualizarFamilia(familia: Familia) {
//        this.familia.postValue(familia)
        viewModelScope.launch {
            repoFamilia.guardarFamilia(familia)
        }
    }


}


