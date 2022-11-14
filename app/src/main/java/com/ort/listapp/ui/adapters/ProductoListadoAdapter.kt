package com.ort.listapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ort.listapp.R
import com.ort.listapp.domain.model.ItemLista
import java.text.DecimalFormat

@SuppressLint("SetTextI18n")
class ProductoListadoAdapter(
    var productos: List<ItemLista>,
    val context: Context,
    var clickEliminar: (ItemLista) -> Unit,
    var clickSumar: (ItemLista) -> Unit,
    var clickRestar: (ItemLista) -> Unit
) : RecyclerView.Adapter<ProductoListadoAdapter.ProductoListadoHolder>() {

    class ProductoListadoHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        val btnDelete: Button
        val btnSumar: Button
        val btnRestar: Button

        init {
            this.view = v
            btnDelete = view.findViewById(R.id.delete)
            btnSumar = view.findViewById(R.id.btnSumarLista)
            btnRestar = view.findViewById(R.id.btnRestarLista)
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

        fun setPrecioMax(precioMax: Double) {
            val txtPrecio: TextView = view.findViewById(R.id.precioItem)
            txtPrecio.text = "$$precioMax"
        }

        fun setUsuario(usuario: String) {
            val txtUsuario: TextView = view.findViewById(R.id.usuarioNombre)
            txtUsuario.text = usuario
        }

        fun setCantidad(cantidad: Int) {
            val txtCantidad: TextView = view.findViewById(R.id.cantidad)
            txtCantidad.text = cantidad.toString()
        }

        fun getName(): TextView {
            return view.findViewById(R.id.nombre)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoListadoHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_producto_lista_de_compras, parent, false)
        return (ProductoListadoHolder(view))
    }

    override fun onBindViewHolder(holder: ProductoListadoHolder, position: Int) {
        val item = productos[position]
        val producto = item.producto
        holder.setNombre(producto.nombre)

        holder.getName().setOnClickListener {
            Snackbar.make(
                it, "${producto.nombre}", Snackbar.LENGTH_SHORT
            ).show()
        }

        val precio = DecimalFormat("#.##").format(producto.precio * item.cantidad).toDouble()
        holder.setPrecioMax(precio)
        holder.setCantidad(item.cantidad)
        holder.setUsuario(item.nombreUsuario)

        holder.btnDelete.setOnClickListener {
            clickEliminar(productos[position])
        }

        holder.btnSumar.setOnClickListener {
            clickSumar(item)
        }

        holder.btnRestar.setOnClickListener {
            if(item.cantidad > 0){
                clickRestar(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}