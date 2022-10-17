package com.ort.listapp.ui.productos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.Producto
import kotlinx.coroutines.launch

class ProductosViewModel : ViewModel() {

    val repositorio = ProductoRepository()
    var listaProdsFavs : MutableList<Producto> = ArrayList<Producto>()
    var listaProdsPersonalizados : MutableList<Producto> = ArrayList<Producto>()
    var listaStock : MutableList<Producto> = ArrayList<Producto>()


    fun cargarProdsPersonalizados(){
        val listaProductosId = listOf(
            "5410171921991",
            "0040000017318",
            "7790250047162",
            "0080432400432",
            "7790895007057",
            "7790742656018",
            "0000075024956",
            "7891000244111",
            "0000075024956",
            "4058075498051",
        )
        viewModelScope.launch {
            val result: MutableList<Producto> = repositorio.getProductosByListaIds(listaProductosId)
            if(result != null){
                result.forEach(){
                    listaProdsPersonalizados.add(it)
                }
                Log.d("FUNCIONA","")
            }else{
                Log.d("NO FUNCIONA","")

            }
        }/*
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
    */}

    fun cargarProdsFav(){
        viewModelScope.launch {
            val listaProductosId = listOf(
                "5410171921991",
                "0040000017318",
                "7790250047162",
                "0080432400432",
                "7790895007057",
                "7790742656018",
                "0000075024956",
                "7891000244111",
                "0000075024956",
                "4058075498051",
            )
            listaProdsFavs = repositorio.getProductosByListaIds(listaProductosId)


            /* listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
    */
        }
    }


    fun cargarStock(){
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))

    }


    val recProdsFavoritos: MutableLiveData<MutableList<Producto>> = MutableLiveData<MutableList<Producto>>().apply {
        cargarProdsFav()
        value = listaProdsFavs
    }


    val recProdsPers: MutableLiveData<MutableList<Producto>> =  MutableLiveData<MutableList<Producto>>().apply {
        cargarProdsPersonalizados()
        value = listaProdsPersonalizados
    }


    val recStock: MutableLiveData<MutableList<Producto>> = MutableLiveData<MutableList<Producto>>().apply {
        cargarStock()
        value = listaStock
    }
    fun agregarProducto(cantidad:Int,idProducto:String){
        //conectar con firebase
        //
    }
}