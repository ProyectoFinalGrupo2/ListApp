package com.ort.listapp.ui.productos

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.ort.listapp.R
import com.ort.listapp.adapters.ProductoAdapter
import com.ort.listapp.databinding.FragmentProductosBinding
import com.ort.listapp.domain.model.Producto
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel

class ProductosFragment : Fragment() {

    private var _binding: FragmentProductosBinding? = null

    private val binding get() = _binding!!

    //    private val listaDeComprasViewModel: ListaDeComprasViewModel by activityViewModels()
    private val viewModel: FamilyViewModel by activityViewModels()

    lateinit var popUp: AlertDialog
    lateinit var popupBuilder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val productosViewModel = ViewModelProvider(this).get(ProductosViewModel::class.java)

        _binding = FragmentProductosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recViewProductosFav: RecyclerView = binding.recViewProductos
        val recViewProdPersonalizados: RecyclerView = binding.listaProdsPersonalizados
        val recViewStock: RecyclerView = binding.recListaStock

        recViewProductosFav.setHasFixedSize(true)
        recViewProductosFav.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        viewModel.getFamilia().observe(viewLifecycleOwner) {
            recViewProductosFav.adapter =
                ProductoAdapter(viewModel.getProductosFavoritos(), requireContext()) { prod ->
                    onItemClick(prod)
                }
        }

        recViewProdPersonalizados.setHasFixedSize(true)
        recViewProdPersonalizados.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        viewModel.getFamilia().observe(viewLifecycleOwner) {
            recViewProdPersonalizados.adapter =
                ProductoAdapter(viewModel.getProductosPersonalizados(), requireContext()) { prod ->
                    onItemClick(prod)
                }
        }

        recViewStock.setHasFixedSize(true)
        recViewStock.layoutManager = GridLayoutManager(requireContext(), 3)
        productosViewModel.recStock.observe(viewLifecycleOwner, Observer { data ->
            recViewStock.adapter = ProductoAdapter(data, requireContext()) { prod ->
                onItemClick(prod)
            }
        })
        return root
    }


    fun onItemClick(producto: Producto) {
        popupBuilder = AlertDialog.Builder(context)
        var popUpView = getLayoutInflater().inflate(R.layout.popup_producto_layout, null)
        var botonAgregar = popUpView.findViewById<Button>(R.id.btn_sumar_agregarprod)
        var botonRestar = popUpView.findViewById<Button>(R.id.btn_restar_agregarprod)
        var cantidad = popUpView.findViewById<TextView>(R.id.txt_cantidad_agregarprod)
        var imagen = popUpView.findViewById<ImageView>(R.id.img_producto_popup)
        var nombreProd = popUpView.findViewById<TextView>(R.id.txt_nom_prod_popup)
        var precioProducto = popUpView.findViewById<TextView>(R.id.txt_precio_prod_popup)
        var subtotal = popUpView.findViewById<TextView>(R.id.txt_subtotal_popup)
        var btnCerrar = popUpView.findViewById<ImageButton>(R.id.btn_cerrar_popup)
        var btnAgregar = popUpView.findViewById<Button>(R.id.btn_agregar_lista)

        var cantActual = 1


        fun actualizarSubtotal() {
            if (cantActual > 0) {
                subtotal.text = "Subtotal: $" + producto.precioMax * cantActual
            } else {
                subtotal.text = ""
            }
        }
        popupBuilder.setView(popUpView)
        popUp = popupBuilder.create()
        popUp.show()
        nombreProd.text = producto.nombre
        precioProducto.text = "$" + producto.precioMax.toString()
        Glide.with(popUpView).load(producto.imgURL()).into(imagen)
        botonAgregar.setOnClickListener {
            cantActual++
            cantidad.text = cantActual.toString()
            actualizarSubtotal()
        }
        botonRestar.setOnClickListener {
            if (cantActual > 1) {
                cantActual--
                cantidad.text = cantActual.toString()
                actualizarSubtotal()
            }
        }

        btnCerrar.setOnClickListener {
            popUp.dismiss()
        }

        btnAgregar.setOnClickListener {
            Snackbar.make(
                popUpView,
                "Se agregó el producto " + producto.nombre + " en " + cantActual + " cantidades",
                Snackbar.LENGTH_SHORT
            ).show()
            //popUp.dismiss()
            viewModel.agregarProductoEnLista(
                TipoLista.LISTA_DE_COMPRAS,
                producto.id,
                cantActual,
                "Martin"
            )
            // Prueba para agregar al mismo producto en favoritos
            viewModel.agregarProductoFavorito(producto.id)
            // Prueba de borrado de productos precargados en ListaCompras y Favoritos
            viewModel.removerProductoDeLista(TipoLista.LISTA_DE_COMPRAS, "4058075498051")
            viewModel.removerProductoFavorito("0080432400432")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

