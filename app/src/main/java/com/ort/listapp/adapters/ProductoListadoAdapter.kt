package com.ort.listapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.listapp.R
import com.ort.listapp.entities.Producto

class ProductoListadoAdapter(
    var productos: MutableList<Producto>,
    val context: Context
) : RecyclerView.Adapter<ProductoListadoAdapter.ProductoListadoHolder>() {


    class ProductoListadoHolder(v: View) : RecyclerView.ViewHolder(v){
        //Se escriben funciones que quiero que se ejecuten cuando se renderice cada item
        private var view: View
        init{
            this.view = v
        }

        fun setNombre(nombre:String){
            var txtNombre : TextView = view.findViewById(R.id.nombreListadoItem)
            txtNombre.text = nombre
        }

        fun setPrecioMax(precioMax:Double){
            var txtPrecio : TextView = view.findViewById(R.id.precioItem)
            txtPrecio.text = ("$" + precioMax.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoListadoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_lista,parent,false)
        return (ProductoListadoHolder(view))
    }

    override fun onBindViewHolder(holder: ProductoListadoHolder, position: Int) {
        holder.setNombre(productos[position].nombre)
        holder.setPrecioMax(productos[position].precioMax)
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}