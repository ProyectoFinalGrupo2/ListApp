package com.ort.listapp.ui.productos

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ort.listapp.domain.model.Producto

class ProductosViewModel : ViewModel() {

    var listaStock: MutableList<Producto> = ArrayList<Producto>()

    val recStock: MutableLiveData<MutableList<Producto>> =
        MutableLiveData<MutableList<Producto>>().apply {
            cargarStock()
            value = listaStock
        }

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

    fun validarFormCrearProd(nombre:EditText,precio:EditText) : Boolean{
        var valido = false
        if(nombre.text.toString().length > 0){
             if(precio.text.toString().length > 0 ){
                 if( precio.text.toString().toDouble() >= 0){
                     valido = true
                 }
             }else{
                 precio.error ="El precio debe ser mayor a $0.00"
             }
         }else{
             nombre.error = "El nombre no puede estar vacío."
         }
        return valido
    }

    fun getCategorias(): MutableList<String>{
        var lista :MutableList<String> = ArrayList<String>()
        lista.add("ALIMENTOS CONGELADOS")
        lista.add("ALMACÉN")
        lista.add("BEBÉS")
        lista.add("BEBIDAS CON ALCOHOL")
        lista.add("BEBIDAS SIN ALCOHOL")
        lista.add("FRESCOS")
        lista.add("LIMPIEZA")
        lista.add("MASCOTAS")
        lista.add("PERFUMERÍA Y CUIDADO PERSONAL")

        return lista
    }
}