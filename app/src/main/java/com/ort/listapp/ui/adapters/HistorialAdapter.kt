package com.ort.listapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.domain.model.Lista
import java.security.Timestamp
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class HistorialAdapter(
    var historiales: List<Lista>,
    val context: Context,
    var onClick: (Lista) -> Unit
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
            txtNombre.text = "Compra " + nombre
        }

        fun setCantidad(cantidad: Int) {
            val txtCantidad: TextView = view.findViewById(R.id.cant)
            if(cantidad == 1){
                txtCantidad.text = "Un solo producto"
            }else{
                txtCantidad.text = "${cantidad} Productos"
            }
        }

        fun setPrecio(precio: Double) {
            val txtPrecio: TextView = view.findViewById(R.id.precio)
            txtPrecio.text = "Total = $${precio.toString().split(".")[0]}"
        }

        fun loadImg(position:Int) {
            val fotoCompra: ImageView = view.findViewById(R.id.fotoHistorial)
            if(position % 2 == 0){
                fotoCompra.setImageResource(R.drawable.cart_icon_1)
            }else{
                fotoCompra.setImageResource(R.drawable.cart_icon_2)
            }
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.card_lista_historial)
        }

        /*fun setLista(productos: MutableList<ItemLista>) {
            var prods : MutableList<ItemLista> = view.findViewById(R.id.listaProds)
            prods = productos
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lista_historial, parent, false)
        return (HistorialHolder(view))
    }

    override fun onBindViewHolder(holder: HistorialHolder, position: Int) {
        historiales[position].nombre?.let { holder.setNombre(it) }
        historiales[position].productos
        holder.loadImg(position)
        holder.setPrecio(precioTotal(historiales[position].productos))
        holder.setCantidad(historiales[position].productos.size)

        holder.getCard().setOnClickListener {
            onClick(historiales[position])
        }
    }

    fun precioTotal(lista: MutableList<ItemLista>): Double {
        var total = 0.0
        lista.forEach {
            total += it.producto.precio * it.cantidad
        }
        return total
    }

    override fun getItemCount(): Int {
        return historiales.size
    }
}