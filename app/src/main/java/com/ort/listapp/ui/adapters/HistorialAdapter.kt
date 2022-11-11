package com.ort.listapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.domain.model.Lista
import java.security.Timestamp


class HistorialAdapter(
    var historiales: List<Lista>,
    val context: Context,
    var onClickLista: (Lista) -> Unit
) : RecyclerView.Adapter<HistorialAdapter.HistorialHolder>() {


    class HistorialHolder(v: View) : RecyclerView.ViewHolder(v) {
        //Se escriben funciones que quiero que se ejecuten cuando se renderice cada item
        private var view: View

        init {
            this.view = v
        }

        //Se hace una función por cada cosa que pasa en el item
        fun setNombre(nombre: String) {
            val txtNombre: TextView = view.findViewById(R.id.nombreLista)
            txtNombre.text = "Compra del " + nombre
        }

        fun setCantidad(cantidad: Int) {
            val txtCantidad: TextView = view.findViewById(R.id.cant)
            if(cantidad == 1){
                txtCantidad.text = "Un solo producto"
            }else{
                txtCantidad.text = "${cantidad.toString()} Productos"
            }
        }

        fun setPrecio(precio: Double) {
            val txtPrecio: TextView = view.findViewById(R.id.precio)
            txtPrecio.text = "Total = $${precio.toString()}"
        }

        fun loadImg(position:Int) {
            val fotoCompra: ImageView = view.findViewById(R.id.fotoHistorial)
            if(position % 2 == 0){
                fotoCompra.setImageResource(R.drawable.cart_icon_1)
            }else{
                fotoCompra.setImageResource(R.drawable.cart_icon_2)
            }
        }

        /*fun getLista(): CardView {
            return view.findViewById(R.id.card_compra_fav)
        }

        fun setLista(productos: MutableList<ItemLista>) {
            var prods : MutableList<ItemLista> = view.findViewById(R.id.listaProds)
            prods = productos
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lista_historial, parent, false)
        return (HistorialHolder(view))
        //val lengthComparator = Comparator { fecha1: Timestamp, fecha2: Timestamp -> fecha1 fecha2 }
        //println(listOf("aaa", "bb", "c").sortedWith(lengthComparator))
        historiales.sortedByDescending {
            it.fechaCreacion
        }
    }

    override fun onBindViewHolder(holder: HistorialHolder, position: Int) {
        historiales[position].nombre?.let { holder.setNombre(it) }
        historiales[position].productos
        holder.loadImg(position)
        holder.setPrecio(precioTotal(historiales[position].productos))
        holder.setCantidad(historiales[position].productos.size)
    }

    fun precioTotal(lista: MutableList<ItemLista>): Double {
        var total = 0.0
        lista.forEach {
            total += it.producto.precio
        }
        return total
    }

    override fun getItemCount(): Int {
        return historiales.size
    }
}