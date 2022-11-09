package com.ort.listapp.ui.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.listapp.R
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.Producto


class CompraFavoritaAdapter(
    var comprasFavoritas: List<Lista>,
    val context: Context,
    var onClickLista: (Lista) -> Unit
) : RecyclerView.Adapter<CompraFavoritaAdapter.CompraFavoritaHolder>() {


    class CompraFavoritaHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View

        init {
            this.view = v
        }
        fun setNombre(nombre: String) {
            val txtNombre: TextView = view.findViewById(R.id.nombreCompraFavorita)
            var nomCompra = nombre
            txtNombre.text = nomCompra
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraFavoritaHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_compra_favorita, parent, false)
        return (CompraFavoritaHolder(view))
    }

    override fun onBindViewHolder(holder: CompraFavoritaHolder, position: Int) {
        //Iteración de la lista y va  usando las funciones set
        //Solamente itera sobre los elementos en pantalla e itera a medida que se scrollea
        comprasFavoritas[position].nombre?.let { holder.setNombre(it) }
        holder.loadImg(position)


        //Se le settea un click listener a las cards
        holder.getLista().setOnClickListener() {
            onClickLista(comprasFavoritas[position])
        }
    }

    override fun getItemCount(): Int {
        return comprasFavoritas.size
    }
}