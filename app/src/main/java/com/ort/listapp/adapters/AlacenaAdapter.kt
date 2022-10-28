package com.ort.listapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.ort.listapp.R
import com.ort.listapp.domain.model.ProductoListado

@SuppressLint("SetTextI18n")
class AlacenaAdapter(
    var productos: List<ProductoListado>,
    val context: Context,
    var onClick: (ProductoListado) -> Unit,
    var onClick2: (ProductoListado) -> Unit
) : RecyclerView.Adapter<AlacenaAdapter.AlacenaHolder>() {

//    private val productoRepository = ProductoRepository()
//    private val prods = productoRepository.getProductosByListaIds(cargarListaIds())
//    var p: Producto? = Producto()

    class AlacenaHolder(v: View) : RecyclerView.ViewHolder(v) {
        //Se escriben funciones que quiero que se ejecuten cuando se renderice cada item
        private var view: View
        val btnAgregar: Button
        val btnRestar: Button

        init {
            this.view = v
            btnAgregar = view.findViewById(R.id.sumar)
            btnRestar = view.findViewById(R.id.sacar)
        }

        fun setNombre(nombre: String) {
            val txtNombre: TextView = view.findViewById(R.id.nombre)
            var nomProd = nombre
            if(nombre.contains(" ")){
                val n = nombre.split(" ")
                nomProd = "${n[0]} ${n[1]}..."
            }
            txtNombre.text = nomProd
        }

        fun setCantidad(cantidad: Int) {
            val txtCantidad: TextView = view.findViewById(R.id.cantidad)
            txtCantidad.text = cantidad.toString()
        }

        fun loadImg(url: String?) {
            val albumCover: ImageView = view.findViewById(R.id.fotoProducto)
            Glide.with(view).load(url).placeholder(R.drawable.placeholder).into(albumCover)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlacenaHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_alacena, parent, false)
        return (AlacenaHolder(view))
    }

    override fun onBindViewHolder(holder: AlacenaAdapter.AlacenaHolder, position: Int) {
        val producto = productos[position]
        holder.setNombre(producto.nombre)
        holder.setCantidad(producto.cantidad)
        holder.loadImg(producto.imgURL())

        holder.btnAgregar.setOnClickListener {
            Snackbar.make(
                it, "Se agrego un " + (producto.nombre ?: String), Snackbar.LENGTH_SHORT
            ).show()
            onClick(producto)
        }
        holder.btnRestar.setOnClickListener {
            if(producto.cantidad > 0){
                Snackbar.make(
                    it, "Se saco un " + (producto.nombre ?: String), Snackbar.LENGTH_SHORT
                ).show()
                onClick2(producto)
            } else{
                Snackbar.make(
                    it, "Ups... parece que ya no tienes mas", Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }

//    fun cargarListaIds(): MutableList<String> {
//        val listaIds: MutableList<String> = arrayListOf()
//        productos.forEach {
//            listaIds.add(it.productoId)
//        }
//        return listaIds
//    }
}