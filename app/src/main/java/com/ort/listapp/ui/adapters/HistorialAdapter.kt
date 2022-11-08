package com.ort.listapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.Producto


class HistorialAdapter(
    var historiales: List<Lista>,
    val context: Context,
    var onClickLista: (Lista) -> Unit
) : RecyclerView.Adapter<HistorialAdapter.HistorialHolder>() {


    class HistorialHolder(v: View) : RecyclerView.ViewHolder(v) {
        //Se escriben funciones que quiero que se ejecuten cuando se renderice cada item
        private var view: View

        init {
            this.view = v
        }

        //Se hace una función por cada cosa que pasa en el item
        fun setNombre(nombre: String) {
            val txtNombre: TextView = view.findViewById(R.id.nombreLista)
            var nomLista = nombre
            /*if (nombre.contains(" ")) {
                val n = nombre.split(" ")
                nomCompra = "${n[0]} ${n[1]}..."
            }*/
            txtNombre.text = nomLista
        }


        fun loadImg(position:Int) {
            val fotoCompra: ImageView = view.findViewById(R.id.fotoCompraFavorita)
            if(position % 2 == 0){
                fotoCompra.setImageResource(R.drawable.cart_icon_1)
            }else{
                fotoCompra.setImageResource(R.drawable.cart_icon_2)
            }
        }

        fun getLista(): CardView {
            return view.findViewById(R.id.card_compra_fav)
        }

        fun setLista(productos: MutableList<ItemLista>) {
            var prods : MutableList<ItemLista> = view.findViewById(R.id.listaProds)
            prods = productos
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lista_historial, parent, false)
        return (HistorialHolder(view))
    }

    override fun onBindViewHolder(holder: HistorialHolder, position: Int) {
        //Iteración de la lista y va  usando las funciones set
        //Solamente itera sobre los elementos en pantalla e itera a medida que se scrollea
        historiales[position].nombre?.let { holder.setNombre(it) }
        //historiales[position].productos?.let { holder.setLista(it) }
        //holder.loadImg(position)


        //Se le settea un click listener a las cards
        /*holder.getLista().setOnClickListener() {
            onClickLista(historiales[position])
        }*/
    }

    override fun getItemCount(): Int {
        return historiales.size
    }
}