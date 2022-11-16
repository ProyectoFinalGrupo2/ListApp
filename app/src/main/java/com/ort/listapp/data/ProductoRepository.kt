package com.ort.listapp.data

import com.ort.listapp.data.model.toPreciosProductos
import com.ort.listapp.data.network.ComparativaService

class ProductoRepository {

    private val api = ComparativaService()

    suspend fun obtenerPrecios(lista: List<String>): Map<String, Double>? {
        return api.getPricesFromIds(lista)?.toPreciosProductos()
    }

}

