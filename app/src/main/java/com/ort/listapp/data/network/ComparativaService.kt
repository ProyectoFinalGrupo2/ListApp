package com.ort.listapp.data.network

import com.ort.listapp.data.model.ComparativaResponse
import com.ort.listapp.utils.RetrofitHelper.getRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ComparativaService() {

    private val api = getRetrofit().create(ComparativaAPIClient::class.java)

    suspend fun getPricesFromIds(lista: List<String>): ComparativaResponse? {
        return withContext(Dispatchers.IO) {
            val suc = "12-1-103,9-1-700,9-3-5263,10-1-219,11-5-2997"
            val response = api.getComparativaByEan(suc, lista.joinToString(","))
            response.body()
        }
    }
}

