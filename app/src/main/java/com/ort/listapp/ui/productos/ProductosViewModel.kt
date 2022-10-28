package com.ort.listapp.ui.productos

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.algolia.search.helper.deserialize
import com.algolia.search.model.search.Query
import com.ort.listapp.domain.model.Producto
import com.ort.listapp.helpers.AlgoliaHelper
import kotlinx.coroutines.launch

class ProductosViewModel : ViewModel() {

    val recStock = MutableLiveData<List<Producto>>()
    private val index = AlgoliaHelper.getIndex()

    fun getProductosByText(searchText: String) {
        val query = Query(searchText)
        viewModelScope.launch {
            val result = index.search(query)
            val hits: List<Producto> = result.hits.deserialize(Producto.serializer())
            recStock.postValue(hits)
        }
    }

    fun validarFormCrearProd(nombre: EditText, precio: EditText): Boolean {
        var valido = false
        if (nombre.text.toString().isNotEmpty()) {
            if (precio.text.toString().isNotEmpty()) {
                if (precio.text.toString().toDouble() >= 0) {
                    valido = true
                }
            } else {
                precio.error = "El precio debe ser mayor a $0.00"
            }
        } else {
            nombre.error = "El nombre no puede estar vacío."
        }
        return valido
    }

    fun getCategorias(): MutableList<String> {
        val lista: MutableList<String> = ArrayList<String>()
        lista.add("ALIMENTOS CONGELADOS")
        lista.add("ALMACÉN")
        lista.add("BEBÉS")
        lista.add("BEBIDAS CON ALCOHOL")
        lista.add("BEBIDAS SIN ALCOHOL")
        lista.add("FRESCOS")
        lista.add("LIMPIEZA")
        lista.add("MASCOTAS")
        lista.add("PERFUMERÍA Y CUIDADO PERSONAL")

        return lista
    }
}