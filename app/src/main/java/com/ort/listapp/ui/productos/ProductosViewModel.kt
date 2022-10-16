package com.ort.listapp.ui.productos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.domain.model.Producto

class ProductosViewModel : ViewModel() {

    var listaProdsFavs : MutableList<com.ort.listapp.domain.model.Producto> = ArrayList<Producto>()

    fun cargarProdsFav(){
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg"))
    }

    private val _recProdsFavoritos = MutableLiveData<List<Producto>>().apply {
        cargarProdsFav()
        value = listaProdsFavs
    }
    val recProdsFavoritos: MutableLiveData<List<Producto>> = _recProdsFavoritos


    //val name = MutableLiveData<String>()
    /*var listaProdsFavs : MutableList<Producto> = ArrayList<Producto>()

    lateinit var texto : String*/



    /*fun cargarProdsFavs(){
        listaProdsFavs.add(Producto("1","Coca Cola 2,5L", 550.00, "https://arcordiezb2c.vteximg.com.br/arquivos/ids/157821-500-400/GASEOSA-COCA-COLA-PET-1-1808.jpg?v=637432184480130000"))
        listaProdsFavs.add(Producto("2", "Pepitos", 250.00,"https://www.distribuidorapop.com.ar/wp-content/uploads/2017/07/galletitas-pepitos-venta.jpg" ))
        listaProdsFavs.add(Producto("3", "Chocolinas",275.00,"https://jumboargentina.vtexassets.com/arquivos/ids/582951/Galletitas-Chocolinas-250-Gr-1-3431.jpg?v=637234676248800000"))
        listaProdsFavs.add(Producto("4", "Oreos",250.50,"https://ardiaprod.vtexassets.com/arquivos/ids/228248/Galletitas-Oreo-118-Gr-_1.jpg?v=637956480395200000"))

    }*/


    /*fun changeName (){
        name.value = "pepe"
    }

    fun changeText (text : String){
        texto = text
    }*/

}