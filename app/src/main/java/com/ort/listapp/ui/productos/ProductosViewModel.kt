package com.ort.listapp.ui.productos

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

}