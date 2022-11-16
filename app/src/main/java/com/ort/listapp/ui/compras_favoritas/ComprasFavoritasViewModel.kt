package com.ort.listapp.ui.compras_favoritas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ort.listapp.R
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.Producto

class ComprasFavoritasViewModel : ViewModel() {

    var listaActual : Lista? = null
    val prodsPasados = "Se pasaron todos los productos a la lista actual"
    val txtAvisoBorrar = "Una vez borrada no podrá recuperarse. En caso de quererla nuevamente, deberá crearla."


    fun hayListaSeleccionada() : Boolean{
        return listaActual != null
    }

    fun getTxtBorrarLista() : String{
        return "¿Está seguro de que quiere borrar la lista "+listaActual?.nombre+" permanentemente?"
    }

}
