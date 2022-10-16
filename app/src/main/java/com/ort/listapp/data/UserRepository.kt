package com.ort.listapp.data

import com.ort.listapp.domain.model.Familia
import com.ort.listapp.domain.model.Usuario

class UserRepository {

    val usuario: Usuario = Usuario(
        "documentId",
        "Pepe",
        "Argento",
        "pepeAr@gmail.com",
        "123",
        listOf("familiaId"))
}