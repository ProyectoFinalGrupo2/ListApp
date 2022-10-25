package com.ort.listapp.ui.productos

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.algolia.search.client.ClientSearch
import com.algolia.search.helper.deserialize
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName
import com.algolia.search.model.search.Query
import com.ort.listapp.domain.model.Producto
import kotlinx.coroutines.launch

class ProductosViewModel : ViewModel() {

    val recStock = MutableLiveData<List<Producto>>()

    private val client = ClientSearch(
        applicationID = ApplicationID("ENBZ8I8018"),
        apiKey = APIKey("404abf3f51b527e0cc1f492c64d0b849"),
    )
    private val indexName = IndexName("productos")
    private val index = client.initIndex(indexName)

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