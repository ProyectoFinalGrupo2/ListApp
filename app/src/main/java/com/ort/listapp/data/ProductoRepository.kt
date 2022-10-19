package com.ort.listapp.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.listapp.domain.model.Producto
import kotlinx.coroutines.tasks.await

class ProductoRepository {

    private var productoList: MutableList<Producto> = mutableListOf()
    val db = Firebase.firestore
    val productosRef = db.collection("productos")

    init {
        productoList.add(
            Producto(
                "5410171921991",
                "01",
                "0108",
                "MC CAIN",
                "Croquetas de Papas Noisettes Mc Cain 1 Kg",
                978.0,
                997.0,
                "1.0 kg"
            )
        )
        productoList.add(
            Producto(
                "0040000017318",
                "02",
                "0208",
                "M&M",
                "Confites de Chocolate M&M 150 Gr",
                1010.0,
                1047.99,
                "150.0 gr"
            )
        )
        productoList.add(
            Producto(
                "7790250047162",
                "03",
                "0302",
                "BABYSEC",
                "Pañal G Babysec Premium 1 U",
                865.65,
                865.65,
                "1.0 un"
            )
        )
        productoList.add(
            Producto(
                "0080432400432",
                "04",
                "0402",
                "CHIVAS REGAL",
                "Whisky 12 Años Chivas Regal 1 Ltr",
                7660.0,
                8190.0,
                "1.0 lt"
            )
        )
        productoList.add(
            Producto(
                "7790895007057",
                "05",
                "0505",
                "COCA COLA",
                "Coca Cola 2.25 Lt y Gaseosa Lima Limon Sprite 2.25 Lt",
                911.73,
                911.73,
                "5.0 lt"
            )
        )
        productoList.add(
            Producto(
                "7790742656018",
                "06",
                "0606",
                "LA SERENISIMA",
                "Bebida Lactea Sabor Vainilla sin TACC La Serenisima 1 Lt",
                340.00,
                342.40,
                "5.0 lt"
            )
        )
        productoList.add(
            Producto(
                "0000077900319",
                "07",
                "0703",
                "OFF!",
                "Repelente Extra Duracion Active Off 90 Gr",
                338.0,
                509.0,
                "90.0 gr"
            )
        )
        productoList.add(
            Producto(
                "7891000244111",
                "08",
                "0801",
                "DOG CHOW",
                "Alimento para Perros Humedo Razas Pequeñas Pollo Dog Chow 100 Gr",
                172.0,
                196.0,
                "100.0 gr"
            )
        )
        productoList.add(
            Producto(
                "0000075024956",
                "09",
                "0902",
                "REXONA",
                "Desodorante Antitranspirante en Barra Men V8 Rexona 50 Gr",
                418.0,
                418.0,
                "50.0 gr"
            )
        )
        productoList.add(
            Producto(
                "4058075498051",
                "70",
                "7004",
                "OSRAM",
                "Lampara Led 5W 220V E27 G4 Calida Osram 1 Un",
                299.0,
                299.0,
                "1.0 un"
            )
        )
    }

    fun getProductos(): MutableList<Producto> {
        return productoList
    }

    suspend fun getProductosByListaIds(lista: List<String>): MutableList<Producto> {
        val listaProductos: MutableList<Producto> = arrayListOf()
        for (productoId in lista) {
            val producto = getProductoById(productoId)
            if (producto != null) {
                listaProductos.add(producto)
            }
        }
        return listaProductos
    }

    suspend fun getProductoById(productoId: String): Producto? {
        val documentSnapshot = productosRef.document(productoId).get().await()
        if (documentSnapshot.exists()) {
            val producto = documentSnapshot.toObject<Producto>()!!
            producto.id = productoId
            return producto
        }
        return null
    }

    fun getProductosFromListaIdsNoAPI(lista: List<String>): MutableList<Producto> {
        val listaProductos: MutableList<Producto> = arrayListOf()
        for (productoId in lista) {
            val producto = productoList.find { it.id == productoId }
            if (producto != null) {
                producto.id = productoId
                listaProductos.add(producto)
            }
        }
        return listaProductos
    }

}