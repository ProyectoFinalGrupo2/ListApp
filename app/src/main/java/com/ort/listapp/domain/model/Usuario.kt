package com.ort.listapp.domain.model

data class Usuario(
    val id: Int,
    val nombre: String,
    val apodo: String,
    val email: String,
    val password: String,
    val familias: List<Familia>
)
