package com.ort.listapp.ui.productos

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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

    private val viewModel: FamilyViewModel by activityViewModels()
    private val productosViewModel: ProductosViewModel by viewModels()

    lateinit var popUp: AlertDialog
    lateinit var popupBuilder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val rvProdFavoritos = binding.rvProdFavoritos
        val rvProdPersonalizados = binding.rvProdPersonalizados
        val rvProdFiltrados = binding.rvProdFiltrados

        val btnCrearProducto = binding.btnNuevoProducto
        val etBuscar = binding.etBuscar

        val tvProdFavoritos = binding.tvProdFavoritos
        val tvProdPersonalizados = binding.tvProdPersonalizados
        val tvProdFiltrados = binding.tvProdFiltrados


        rvProdFavoritos.setHasFixedSize(true)
        rvProdFavoritos.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        viewModel.getFamilia().observe(viewLifecycleOwner) {
            rvProdFavoritos.adapter =
                ProductoAdapter(viewModel.getProductosFavoritos(), requireContext()) { prod ->
                    onItemClick(prod)
                }
        }

        rvProdPersonalizados.setHasFixedSize(true)
        rvProdPersonalizados.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        viewModel.getFamilia().observe(viewLifecycleOwner) {
            rvProdPersonalizados.adapter =
                ProductoAdapter(viewModel.getProductosPersonalizados(), requireContext()) { prod ->
                    onItemClick(prod)
                }
        }

        rvProdFiltrados.setHasFixedSize(true)
        rvProdFiltrados.layoutManager = GridLayoutManager(requireContext(), 3)
        productosViewModel.recStock.observe(viewLifecycleOwner) {
            rvProdFiltrados.adapter = ProductoAdapter(it, requireContext()) { prod ->
                onItemClick(prod)
            }
        }

        btnCrearProducto.setOnClickListener {
            crearProductoPersonalizado()
        }

        etBuscar.doAfterTextChanged {
            if (etBuscar.text.toString().length > 2) {
                productosViewModel.getProductosByText(etBuscar.text.toString())
                tvProdFavoritos.visibility = View.GONE
                rvProdFavoritos.visibility = View.GONE
                tvProdPersonalizados.visibility = View.GONE
                rvProdPersonalizados.visibility = View.GONE
                tvProdFiltrados.visibility = View.VISIBLE
                rvProdFiltrados.visibility = View.VISIBLE
            } else {
                tvProdFavoritos.visibility = View.VISIBLE
                rvProdFavoritos.visibility = View.VISIBLE
                tvProdPersonalizados.visibility = View.VISIBLE
                rvProdPersonalizados.visibility = View.VISIBLE
                tvProdFiltrados.visibility = View.GONE
                rvProdFiltrados.visibility = View.GONE
            }
        }
    }

    fun crearProductoPersonalizado() {
        popupBuilder = AlertDialog.Builder(context)
        val popUpView = getLayoutInflater().inflate(R.layout.popup_crear_producto, null)
        val btnCerrar = popUpView.findViewById<ImageButton>(R.id.btn_cerrar_popup)
        val btnCrear = popUpView.findViewById<Button>(R.id.btn_crear_producto)
        val nombreProd = popUpView.findViewById<EditText>(R.id.txt_producto_pers_nombre)
        val precioProducto = popUpView.findViewById<EditText>(R.id.txt_producto_pers_precio)
        val spinner = popUpView.findViewById<Spinner>(R.id.txt_producto_pers_categoria)
        val switchFav = popUpView.findViewById<Switch>(R.id.switch_prod_pers_fav)

        popupBuilder.setView(popUpView)
        popUp = popupBuilder.create()
        val adapter = ArrayAdapter(
            popUp.context,
            android.R.layout.simple_list_item_1,
            productosViewModel.getCategorias()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        popUp.show()

        btnCrear.setOnClickListener {
            val valido = productosViewModel.validarFormCrearProd(nombreProd, precioProducto)
            if (valido) {
                val idProdCreado = viewModel.agregarProductoPersonalizado(
                    nombreProd.text.toString(),
                    precioProducto.text.toString().toDouble(),
                    spinner.toString()
                )
                if(switchFav.isChecked){
                    viewModel.agregarProductoFavorito(idProdCreado)
                }
                popUp.dismiss()
            }
        }

        btnCerrar.setOnClickListener {
            popUp.dismiss()
        }

    }

    @SuppressLint("SetTextI18n")
    fun onItemClick(producto: Producto) {
        popupBuilder = AlertDialog.Builder(context)
        val popUpView = getLayoutInflater().inflate(R.layout.popup_producto_layout, null)
        val botonAgregar = popUpView.findViewById<Button>(R.id.btn_sumar_agregarprod)
        val botonRestar = popUpView.findViewById<Button>(R.id.btn_restar_agregarprod)
        val cantidad = popUpView.findViewById<TextView>(R.id.txt_cantidad_agregarprod)
        val imagen = popUpView.findViewById<ImageView>(R.id.img_producto_popup)
        val nombreProd = popUpView.findViewById<TextView>(R.id.txt_nom_prod_popup)
        val precioProducto = popUpView.findViewById<TextView>(R.id.txt_precio_prod_popup)
        val subtotal = popUpView.findViewById<TextView>(R.id.txt_subtotal_popup)
        val btnCerrar = popUpView.findViewById<ImageButton>(R.id.btn_cerrar_popup)
        val btnAgregar = popUpView.findViewById<Button>(R.id.btn_crear_producto)
        var cantActual = 1


        fun actualizarSubtotal() {
            if (cantActual > 0) {
                subtotal.text = "Subtotal: $${producto.precioMax * cantActual}"
            } else {
                subtotal.text = ""
            }
        }
        popupBuilder.setView(popUpView)
        popUp = popupBuilder.create()
        popUp.show()
        nombreProd.text = producto.nombre
        precioProducto.text = "$${producto.precioMax}"
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
            viewModel.agregarProductoEnLista(
                TipoLista.LISTA_DE_COMPRAS,
                producto.id,
                cantActual,
                "Martin"
            )
            // Prueba para agregar al mismo producto en favoritos
//            viewModel.agregarProductoFavorito(producto.id)
            // Prueba de borrado de productos precargados en ListaCompras y Favoritos
            //viewModel.removerProductoDeLista(TipoLista.LISTA_DE_COMPRAS, "4058075498051")
            //viewModel.eliminarProductoFavorito("0080432400432")
            popUp.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

