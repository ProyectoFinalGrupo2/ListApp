package com.ort.listapp.data.network

import com.ort.listapp.data.model.ComparativaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ComparativaAPIClient {

    @GET("comparativa")
    suspend fun getComparativaByEan(
        @Query("array_sucursales") suc: String,
        @Query("array_productos") ean: String,
    ): Response<ComparativaResponse>
}