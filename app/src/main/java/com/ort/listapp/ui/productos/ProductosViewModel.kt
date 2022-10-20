package com.ort.listapp.ui.productos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ort.listapp.domain.model.Producto

class ProductosViewModel : ViewModel() {

    //    val repositorio = ProductoRepository()
//    var listaProdsFavs: MutableList<Producto> = ArrayList<Producto>()
//    var listaProdsPersonalizados: MutableList<Producto> = ArrayList<Producto>()
    var listaStock: MutableList<Producto> = ArrayList<Producto>()

//    val recProdsFavoritos: MutableLiveData<MutableList<Producto>> =
//        MutableLiveData<MutableList<Producto>>().apply {
//            cargarProdsFav()
//        }
//
//    val recProdsPers: MutableLiveData<MutableList<Producto>> =
//        MutableLiveData<MutableList<Producto>>().apply {
//            cargarProdsPersonalizados()
//        }

    val recStock: MutableLiveData<MutableList<Producto>> =
        MutableLiveData<MutableList<Producto>>().apply {
            cargarStock()
            value = listaStock
        }

//    fun cargarProdsPersonalizados() {
//        val listaProductosId = listOf(
//            "5410171921991",
//            "0040000017318",
//            "7790250047162",
//            "0080432400432",
//            "7790895007057",
//            "0000077900319",
//            "7790742656018",
//            "7891000244111",
//            "0000075024956",
//            "4058075498051",
//        )
//        viewModelScope.launch {
//            val result: MutableList<Producto> = repositorio.getProductosByListaIds(listaProductosId)
//            if (result != null) {
//                recProdsPers.postValue(result)
//            }
//        }
//    }

//    fun cargarProdsFav() {
//        viewModelScope.launch {
//            val listaProductosId = listOf(
//                "7790250047162",
//                "5410171921991",
//                "0040000017318",
//                "7891000244111",
//                "0000075024956",
//                "0080432400432",
//                "4058075498051",
//                "7790895007057",
//                "7790742656018",
//            )
//            viewModelScope.launch {
//                val result: MutableList<Producto> =
//                    repositorio.getProductosByListaIds(listaProductosId)
//                if (result != null) {
//                    recProdsFavoritos.postValue(result)
//                }
//            }
//        }
//    }


    fun cargarStock() {
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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
        listaStock.add(
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

    }


    fun agregarProducto(cantidad: Int, idProducto: String) {
        //conectar con firebase
        //
    }
}