package com.ort.listapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.listapp.R
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.Producto
import com.ort.listapp.domain.model.ProductoListado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AlacenaAdapter(
    var productos: List<ProductoListado>,
    val context: Context
) : RecyclerView.Adapter<AlacenaAdapter.AlacenaHolder>() {

    class AlacenaHolder(v: View) : RecyclerView.ViewHolder(v) {
        //Se escriben funciones que quiero que se ejecuten cuando se renderice cada item
        private var view: View

        init {
            this.view = v
        }

        fun setNombre(nombre: String) {
            var txtNombre: TextView = view.findViewById(R.id.nombre)
            val nomProd = nombre.split(' ')
            txtNombre.text = nomProd[0] + " " + nomProd[1] + "..."
        }

        fun setCantidad(cantidad: Int) {
            var txtCantidad: TextView = view.findViewById(R.id.cantidad)
            txtCantidad.text = cantidad.toString()
        }

        fun loadImg(url: String?) {
            val albumCover: ImageView = view.findViewById(R.id.fotoProducto)
            Glide.with(view).load(url).placeholder(R.drawable.placeholder).into(albumCover)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlacenaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto_alacena, parent, false)
        return (AlacenaHolder(view))
    }

    val repo = ProductoRepository()
    val prods = runBlocking {
        withContext(Dispatchers.Default) {
            repo.getProductosByListaIds(cargarListaIds())
        }
    }
    var p: Producto? = Producto()

    override fun onBindViewHolder(holder: AlacenaHolder, position: Int) {
        p = prods.find { it.id == prods[position].id }
        holder.setNombre(p!!.nombre)
        holder.setCantidad(productos[position].cantidad)
        holder.loadImg(p!!.imgURL())
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    fun cargarListaIds(): MutableList<String> {
        val listaIds: MutableList<String> = arrayListOf()
        productos.forEach {
            listaIds.add(it.productoId)
        }
        return listaIds
    }
}