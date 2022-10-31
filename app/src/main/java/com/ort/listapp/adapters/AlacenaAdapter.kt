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
import com.ort.listapp.domain.model.ItemLista

@SuppressLint("SetTextI18n")
class AlacenaAdapter(
    var productos: List<ItemLista>,
    val context: Context,
    var clickSuma: (ItemLista) -> Unit,
    var clickResta: (ItemLista) -> Unit
) : RecyclerView.Adapter<AlacenaAdapter.AlacenaHolder>() {

    class AlacenaHolder(v: View) : RecyclerView.ViewHolder(v) {

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
            if (nombre.contains(" ")) {
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
        val item = productos[position]
        val producto = item.producto
        holder.setNombre(producto.nombre)
        holder.setCantidad(item.cantidad)
        holder.loadImg(producto.imgURL())

        holder.btnAgregar.setOnClickListener {
            Snackbar.make(
                it, "Se agrego un " + (producto.nombre), Snackbar.LENGTH_SHORT
            ).show()
            clickSuma(item)
        }
        holder.btnRestar.setOnClickListener {
            if (item.cantidad > 0) {
                Snackbar.make(
                    it, "Se saco un " + (producto.nombre), Snackbar.LENGTH_SHORT
                ).show()
                clickResta(item)
            } else {
                Snackbar.make(
                    it, "Ups... parece que ya no tienes mas", Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}