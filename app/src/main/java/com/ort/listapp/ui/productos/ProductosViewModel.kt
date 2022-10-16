package com.ort.listapp.ui.productos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.domain.model.Producto

class ProductosViewModel : ViewModel() {

    var listaProdsFavs : MutableList<Producto> = ArrayList<Producto>()
    var listaProdsPersonalizados : MutableList<Producto> = ArrayList<Producto>()
    var listaStock : MutableList<Producto> = ArrayList<Producto>()

    fun cargarProdsPersonalizados(){
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsPersonalizados.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
    }

    fun cargarProdsFav(){
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
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

    private val _recProdsFavoritos = MutableLiveData<MutableList<Producto>>().apply {
        cargarProdsFav()
        value = listaProdsFavs
    }
    val recProdsFavoritos: MutableLiveData<MutableList<Producto>> = _recProdsFavoritos

    private val _recProdsPers = MutableLiveData<MutableList<Producto>>().apply {
        cargarProdsPersonalizados()
        value = listaProdsPersonalizados
    }
    val recProdsPers: MutableLiveData<MutableList<Producto>> = _recProdsPers

    private val _recStock = MutableLiveData<MutableList<Producto>>().apply {
        cargarStock()
        value = listaStock
    }
    val recStock: MutableLiveData<MutableList<Producto>> = _recStock

    fun agregarProducto(cantidad:Int,idProducto:String){
        //conectar con firebase
    }
}