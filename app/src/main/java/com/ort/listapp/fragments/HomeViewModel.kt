package com.ort.listapp.fragments

import androidx.lifecycle.ViewModel
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.entities.Producto

class HomeViewModel : ViewModel() {

    val repo = ProductoRepository()

    val prs = repo.getProductos()

    var lista : MutableList<Producto> = ArrayList<Producto>()

    fun cargarProdsFavs(){
        prs.forEach {
            lista.add(Producto(it.id, it.nombre, it.precioMax, it.imgURL))
        }
    }

}