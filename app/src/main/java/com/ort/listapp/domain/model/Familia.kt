package com.ort.listapp.domain.model

data class Familia(
    val id: Int,
    val nombre: String,
    val password: String,
    val usuarios: List<Usuario>
)
