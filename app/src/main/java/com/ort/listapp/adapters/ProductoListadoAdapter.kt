package com.ort.listapp.adapters

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
import com.ort.listapp.domain.model.ProductoListado

@SuppressLint("SetTextI18n")
class ProductoListadoAdapter(
    var productos: List<ProductoListado>,
    val context: Context,
    var onClick: (ProductoListado) -> Unit
) : RecyclerView.Adapter<ProductoListadoAdapter.ProductoListadoHolder>() {

//    private val productoRepository = ProductoRepository()
//    private val prods = productoRepository.getProductosByListaIds(cargarListaIds())
//    var p: Producto? = Producto()

    class ProductoListadoHolder(v: View) : RecyclerView.ViewHolder(v) {
        //Se escriben funciones que quiero que se ejecuten cuando se renderice cada item
        private var view: View
        val btnDelete: Button

        init {
            this.view = v
            btnDelete = view.findViewById(R.id.delete)
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

        fun getButton(): Button {
            return view.findViewById(R.id.delete)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoListadoHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_lista, parent, false)
        return (ProductoListadoHolder(view))
    }

    override fun onBindViewHolder(holder: ProductoListadoHolder, position: Int) {
        val producto = productos[position]
        holder.setNombre(producto.nombre)
        holder.setPrecioMax(producto.precio)
        holder.setCantidad(producto.cantidad)
        holder.setUsuario(producto.nombreUsuario)

        holder.btnDelete.setOnClickListener {
            onClick(productos[position])
            Snackbar.make(
                it, "Se elimino ${producto.nombre}", Snackbar.LENGTH_SHORT
            ).show()
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