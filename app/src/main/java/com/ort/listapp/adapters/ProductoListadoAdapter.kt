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
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.Producto
import com.ort.listapp.domain.model.ProductoListado

class ProductoListadoAdapter(
    var productos: MutableList<ProductoListado>,
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

        fun setUsuario(usuario:String){
            var txtUsuario : TextView = view.findViewById(R.id.usuarioNombre)
            txtUsuario.text = usuario
        }

        fun setCantidad(cantidad:Int){
            var txtCantidad : TextView = view.findViewById(R.id.cantidad)
            txtCantidad.text = cantidad.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoListadoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_lista,parent,false)
        return (ProductoListadoHolder(view))
    }

    val repo = ProductoRepository()
    val prods = repo.getProductosFromListaIdsNoAPI(cargarListaIds())

    override fun onBindViewHolder(holder: ProductoListadoHolder, position: Int) {
        val p = prods.find { it.id == prods[position].id }
        holder.setNombre(p!!.nombre)
        holder.setPrecioMax(p!!.precioMax)
        holder.setCantidad(productos[position].cantidad)
        holder.setUsuario(productos[position].usuarioId)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    fun cargarListaIds(): MutableList<String>{
        val listaIds: MutableList<String> = arrayListOf()
        productos.forEach {
            listaIds.add(it.productoId)
        }
        return listaIds
    }
}