package com.ort.listapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ort.listapp.R
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.Producto
import com.ort.listapp.domain.model.ProductoListado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ProductoListadoAdapter(
    var productos: List<ProductoListado>,
    val context: Context,
    var onClick: (ProductoListado) -> Unit
) : RecyclerView.Adapter<ProductoListadoAdapter.ProductoListadoHolder>() {

    class ProductoListadoHolder(v: View) : RecyclerView.ViewHolder(v) {
        //Se escriben funciones que quiero que se ejecuten cuando se renderice cada item
        private var view: View
        public val btnDelete: Button

        init {
            this.view = v
            btnDelete = view.findViewById(R.id.delete)
        }

        fun setNombre(nombre: String) {
            var txtNombre: TextView = view.findViewById(R.id.nombre)
            val nomProd = nombre.split(' ')
            txtNombre.text = nomProd[0] + " " + nomProd[1] + "..."
        }

        fun setPrecioMax(precioMax: Double) {
            var txtPrecio: TextView = view.findViewById(R.id.precioItem)
            txtPrecio.text = ("$" + precioMax.toString())
        }

        fun setUsuario(usuario: String) {
            var txtUsuario: TextView = view.findViewById(R.id.usuarioNombre)
            txtUsuario.text = usuario
        }

        fun setCantidad(cantidad: Int) {
            var txtCantidad: TextView = view.findViewById(R.id.cantidad)
            txtCantidad.text = cantidad.toString()
        }

        fun getButton(): Button {
            return view.findViewById(R.id.delete)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoListadoHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_lista, parent, false)
        return (ProductoListadoHolder(view))
    }

    val repo = ProductoRepository()
    val prods = runBlocking {
        withContext(Dispatchers.Default) {
            repo.getProductosByListaIds(cargarListaIds())
        }
    }
    var p: Producto? = Producto()

    override fun onBindViewHolder(holder: ProductoListadoHolder, position: Int) {
        p = prods.find { it.id == prods[position].id }
        p?.let { holder.setNombre(it.nombre) }
        p?.let { holder.setPrecioMax(it.precioMax) }
        holder.setCantidad(productos[position].cantidad)
        holder.setUsuario(productos[position].usuarioId)

        holder.btnDelete.setOnClickListener {
            onClick(productos[position])
            Snackbar.make(
                it,"Se elimino " + (p?.nombre ?: String), Snackbar.LENGTH_SHORT).show()

        }

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