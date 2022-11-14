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
import com.ort.listapp.ListaAppApplication.Companion.prefsHelper
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentProductosBinding
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.domain.model.Producto
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.ProductoAdapter
import com.ort.listapp.ui.adapters.ProductoFiltradoAdapter
import com.ort.listapp.utils.SysConstants.PREFIJO_PROD_PERS
import java.text.DecimalFormat

@SuppressLint("SetTextI18n")
class ProductosFragment : Fragment() {

    private var _binding: FragmentProductosBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FamilyViewModel by activityViewModels()
    private val productosViewModel: ProductosViewModel by viewModels()

    private lateinit var rvProdFavoritos: RecyclerView
    private lateinit var rvProdPersonalizados: RecyclerView
    private lateinit var rvProdFiltrados: RecyclerView

    private lateinit var popUp: AlertDialog
    private lateinit var popupBuilder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductosBinding.inflate(inflater, container, false)

        // This is the appropriate place to set up the initial state of your view, to start observing
        // LiveData instances whose callbacks update the fragment's view, and to set up adapters on
        // any RecyclerView or ViewPager2 instances in your fragment's view.
        // from: https://developer.android.com/guide/fragments/lifecycle
        initRecyclersViews()

        return binding.root
    }

    private fun initRecyclersViews() {
        rvProdFavoritos = binding.rvProdFavoritos
        rvProdFavoritos.setHasFixedSize(true)
        rvProdFavoritos.layoutManager =
            GridLayoutManager(requireContext(), 3)

        rvProdPersonalizados = binding.rvProdPersonalizados
        rvProdPersonalizados.setHasFixedSize(true)
        rvProdPersonalizados.layoutManager =
            GridLayoutManager(requireContext(), 3)

        rvProdFiltrados = binding.rvProdFiltrados
        rvProdFiltrados.setHasFixedSize(true)
        rvProdFiltrados.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onStart() {
        super.onStart()

        val btnCrearProducto = binding.btnNuevoProducto
        val etBuscar = binding.editTextBuscar.editText!!

        val tvProdFavoritos = binding.tvProdFavoritos
        val tvProdPersonalizados = binding.tvProdPersonalizados
        val tvProdFiltrados = binding.tvProdFiltrados

        viewModel.getFamilia().observe(viewLifecycleOwner) {
            rvProdFavoritos.adapter =
                ProductoAdapter(viewModel.getProductosFavoritos(), requireContext()) { prod ->
                    onItemClick(prod)
                }
        }

        viewModel.getFamilia().observe(viewLifecycleOwner) {
            rvProdPersonalizados.adapter =
                ProductoAdapter(viewModel.getProductosPersonalizados(), requireContext()) { prod ->
                    onItemClick(prod)
                }
        }

        productosViewModel.recStock.observe(viewLifecycleOwner) {
            rvProdFiltrados.adapter = ProductoFiltradoAdapter(it, requireContext()) { prod ->
                onItemClick(prod)
            }
        }

        btnCrearProducto.setOnClickListener {
            crearProductoPersonalizado()
        }

        fun visibilidadProdFavYPers(visibility: Int) {
            tvProdFavoritos.visibility = visibility
            rvProdFavoritos.visibility = visibility
            tvProdPersonalizados.visibility = visibility
            rvProdPersonalizados.visibility = visibility
        }

        fun visibilidadProdFiltrados(visibility: Int) {
//            tvProdFiltrados.visibility = visibility
            rvProdFiltrados.visibility = visibility
        }

        etBuscar.doAfterTextChanged {
            if (etBuscar.text.toString().length > 2) {
                productosViewModel.getProductosByText(etBuscar.text.toString())
                visibilidadProdFavYPers(View.GONE)
                visibilidadProdFiltrados(View.VISIBLE)
            } else {
                visibilidadProdFavYPers(View.VISIBLE)
                visibilidadProdFiltrados(View.GONE)
            }
        }

    }

    private fun crearProductoPersonalizado() {
        popupBuilder = AlertDialog.Builder(context)
        val popUpView = layoutInflater.inflate(R.layout.popup_crear_producto, null)
        val btnCerrar = popUpView.findViewById<ImageView>(R.id.btn_cerrar_popup)
        val btnCrear = popUpView.findViewById<Button>(R.id.btn_crear_lista)
        val nombreProd = popUpView.findViewById<EditText>(R.id.txt_producto_pers_nombre)
        val precioProducto = popUpView.findViewById<EditText>(R.id.txt_producto_pers_precio)
        val spinner = popUpView.findViewById<Spinner>(R.id.txt_producto_pers_categoria)

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
                val producto =
                    Producto(
                        id = "${PREFIJO_PROD_PERS}${System.currentTimeMillis()}",
                        id_Categoria = spinner.selectedItem.toString(),
                        nombre = nombreProd.text.toString(),
                        precio = precioProducto.text.toString().toDouble(),
                    )
                viewModel.agregarProductoPersonalizado(producto)
                popUp.dismiss()
            }
        }

        btnCerrar.setOnClickListener {
            popUp.dismiss()
        }

    }

    fun onItemClick(producto: Producto) {
        popupBuilder = AlertDialog.Builder(context)
        val popUpView = layoutInflater.inflate(R.layout.popup_producto_layout, null)
        val botonAgregar = popUpView.findViewById<Button>(R.id.btn_sumar_agregarprod)
        val botonRestar = popUpView.findViewById<Button>(R.id.btn_restar_agregarprod)
        val cantidad = popUpView.findViewById<TextView>(R.id.txt_cantidad_agregarprod)
        val imagen = popUpView.findViewById<ImageView>(R.id.img_producto_popup)
        val nombreProd = popUpView.findViewById<TextView>(R.id.txt_nom_prod_popup)
        val precioProducto = popUpView.findViewById<TextView>(R.id.txt_precio_prod_popup)
        val subtotal = popUpView.findViewById<TextView>(R.id.txt_subtotal_popup)
        val btnCerrar = popUpView.findViewById<ImageView>(R.id.btn_cerrar_popup)
        val btnAgregar = popUpView.findViewById<Button>(R.id.btn_crear_lista)
        val btnEditar = popUpView.findViewById<Button>(R.id.btn_editar_producto)
        val corazonFav = popUpView.findViewById<ImageView>(R.id.btn_corazon_fav)
        var cantActual = 1
        var esFavorito = viewModel.esProductoFav(producto)

        if (producto.id.contains(PREFIJO_PROD_PERS)) {
            btnEditar.visibility = View.VISIBLE
        }

        fun actualizarSubtotal() {
            if (cantActual > 0) {
                subtotal.text =
                    "Subtotal: $${DecimalFormat("#.##").format(producto.precio * cantActual)}"
            } else {
                subtotal.text = ""
            }
        }

        fun marcarCorazon() {
            if (esFavorito) {
                corazonFav.setImageResource(R.drawable.corazon_marcado)
            } else {
                corazonFav.setImageResource(R.drawable.corazon_desmarcado)
            }
        }

        popupBuilder.setView(popUpView)
        popUp = popupBuilder.create()
        popUp.show()
        nombreProd.text = producto.nombre
        precioProducto.text = "$${producto.precio}"
        Glide.with(popUpView).load(producto.imgURL())
            .placeholder(R.drawable.productos_icon_placeholder).into(imagen)
        marcarCorazon()

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
            viewModel.agregarProductoEnListaById(
                viewModel.getIdListaDeComprasActual(),
                ItemLista(
                    cantidad = cantActual,
                    producto = producto,
                    nombreUsuario = prefsHelper.getUserName(),
                ),
            )
            Snackbar.make(
                it, "Agregaste $cantActual ${producto.nombre}/s", Snackbar.LENGTH_SHORT
            ).show()
            popUp.dismiss()
        }

        btnEditar.setOnClickListener {
            popUp.dismiss()
            editarProducto(producto)
        }
        corazonFav.setOnClickListener {
            esFavorito = !esFavorito
            if (esFavorito) {
                viewModel.agregarProductoFavorito(producto)
            } else {
                viewModel.eliminarProductoFavorito(producto)
            }
            marcarCorazon()
        }
    }

    private fun editarProducto(producto: Producto) {
        popupBuilder = AlertDialog.Builder(context)
        val popUpView = layoutInflater.inflate(R.layout.popup_crear_producto, null)
        val btnCerrar = popUpView.findViewById<ImageView>(R.id.btn_cerrar_popup)
        val btnBorrar = popUpView.findViewById<Button>(R.id.btn_borrar_producto)
        val btnEditar = popUpView.findViewById<Button>(R.id.btn_crear_lista)
        val nombreProd = popUpView.findViewById<EditText>(R.id.txt_producto_pers_nombre)
        val precioProducto = popUpView.findViewById<EditText>(R.id.txt_producto_pers_precio)
        val spinner = popUpView.findViewById<Spinner>(R.id.txt_producto_pers_categoria)

        btnEditar.text = "Confirmar Edición"
        btnBorrar.visibility = View.VISIBLE
        btnBorrar.text = "Borrar Producto"
        nombreProd.setText(producto.nombre)
        precioProducto.setText(producto.precio.toString())
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

        btnEditar.setOnClickListener {
            val valido = productosViewModel.validarFormCrearProd(nombreProd, precioProducto)
            if (valido) {
                viewModel.actualizarProductoPersonalizado(
                    producto.id,
                    nombreProd.text.toString(),
                    precioProducto.text.toString().toDouble(),
                    spinner.selectedItem.toString()
                )
                popUp.dismiss()
            }
        }

        btnBorrar.setOnClickListener {
            viewModel.eliminarProductoPersonalizado(producto)
            popUp.dismiss()
        }

        btnCerrar.setOnClickListener {
            popUp.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

