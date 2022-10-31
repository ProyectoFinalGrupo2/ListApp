package com.ort.listapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.domain.model.ItemLista

class RealizarCompraAdapter (var listaProductos : List<ItemLista>) : RecyclerView.Adapter<RealizarCompraAdapter.RealizarCompraHolder>(){

    class RealizarCompraHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View

        init {
            this.view = v
        }

        fun setNombre(nombre: String) {
            val txtNombre: TextView = view.findViewById(R.id.txtNombreProductoRC)
            var nomProd = nombre
            if (nombre.contains(" ")) {
                val n = nombre.split(" ")
                nomProd = "${n[0]} ${n[1]}..."
            }
            txtNombre.text = nomProd
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealizarCompraHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_producto_realizar_compra, parent, false)
        return (RealizarCompraHolder(view))
    }

    override fun onBindViewHolder(holder: RealizarCompraHolder, position: Int) {
        holder.setNombre(listaProductos[position].producto.nombre)
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }
}