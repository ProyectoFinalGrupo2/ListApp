package com.ort.listapp.entities

class Producto (
    val id: String,
    val id_Categoria: String,
    val id_subCategoria: String,
    val marca: String,
    val nombre: String,
    val precioMin : Double,
    val precioMax : Double,
    val presentacion: String,
    val imgURL: String = "https://imagenes.preciosclaros.gob.ar/productos/${id}.jpg"
)
