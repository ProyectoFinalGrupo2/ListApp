package com.ort.listapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.domain.model.ItemListaChecklist

class RealizarCompraAdapter(var listaProductos: MutableList<ItemListaChecklist>, var clickChecklist:(String) -> Unit) :
    RecyclerView.Adapter<RealizarCompraAdapter.RealizarCompraHolder>() {

    class RealizarCompraHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        val cbProducto: CheckBox

        init {
            this.view = v
            cbProducto = view.findViewById<CheckBox>(R.id.cbProductoText)
        }

        fun setNombre(nombre: String) {
//            val cbNombre: TextView = view.findViewById(R.id.tvNombreProducto)
//            var nomProd = nombre
//            cbNombre.text = nomProd

            val cbProducto: CheckBox = view.findViewById(R.id.cbProductoText)
            cbProducto.text = nombre
        }

        fun setCantidad(cant:Int){
            val txtCantidad: TextView = view.findViewById(R.id.txtCantidadRC)
            txtCantidad.text= "x" + cant.toString()
        }

        fun setPrecio(precio: Double){
            val txtPrecio: TextView = view.findViewById(R.id.txtPrecioRC)
            txtPrecio.text = "$" + precio.toString()
        }

        fun setChecked(checked: Boolean){
            val cbProducto: CheckBox = view.findViewById(R.id.cbProductoText)
            cbProducto.isChecked = checked
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealizarCompraHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_producto_realizar_compra, parent, false)
        return (RealizarCompraHolder(view))
    }

    override fun onBindViewHolder(holder: RealizarCompraHolder, position: Int) {
        holder.setNombre(listaProductos[position].producto.nombre)
        holder.setCantidad(listaProductos[position].cantidad)
        holder.setPrecio(listaProductos[position].producto.precio)
        holder.setChecked(listaProductos[position].estado)

        holder.cbProducto.setOnClickListener { clickChecklist(listaProductos[position].producto.id) }
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }
}