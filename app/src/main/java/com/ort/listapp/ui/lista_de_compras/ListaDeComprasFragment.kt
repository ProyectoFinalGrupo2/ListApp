package com.ort.listapp.ui.lista_de_compras

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentListaDeComprasBinding
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.ProductoListadoAdapter
import com.ort.listapp.ui.adapters.RealizarCompraAdapter

@SuppressLint("SetTextI18n")
class ListaDeComprasFragment : Fragment() {

    private lateinit var binding: FragmentListaDeComprasBinding

    private val viewModel: FamilyViewModel by activityViewModels()

    private lateinit var popup: AlertDialog
    private lateinit var popupBuilder: AlertDialog.Builder
    private lateinit var adapterRC: RealizarCompraAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListaDeComprasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val btnRealizarCompra = binding.btnRealizarCompra
        val btnEditarLista = binding.btnEditarLista
        val btnComprasFavoritas = binding.btnComprasFavoritas
        val btnAgregarListaFav = binding.btnAgregarListaFav
        val txtPrecioTotalLista = binding.txtPrecioTotalLista
        var rvListaCompras = binding.rvListaCompra

        viewModel.getFamilia().observe(this) {
            val total = viewModel.precioTotalListaById(viewModel.getIdListaDeComprasActual())
                .toString()
            txtPrecioTotalLista.text = "Precio total: $$total"
            val productos = viewModel.getProductosByIdLista(viewModel.getIdListaDeComprasActual())
            rvListaCompras.setHasFixedSize(true)
            rvListaCompras.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rvListaCompras.adapter = ProductoListadoAdapter(
                productos, requireContext(),
                { removerProducto(it) },
                { clickSumaYResta(it, 1) },
                { clickSumaYResta(it, -1) }
            )
        }


        btnRealizarCompra.setOnClickListener {
            realizarCompra()
        }

        btnEditarLista.setOnClickListener {
            editarLista()
        }

        btnComprasFavoritas.setOnClickListener {
            val action =
                ListaDeComprasFragmentDirections.actionListaDeComprasFragmentToComprasFavoritasFragment()
            view?.findNavController()?.navigate(action)
//            this.findNavController().navigate(action)
        }

        btnAgregarListaFav.setOnClickListener {
            popupBuilder = AlertDialog.Builder(context)
            val popupView = layoutInflater.inflate(R.layout.popup_crear_compra_fav, null)
            val nombreLista = popupView.findViewById<EditText>(R.id.txt_nombre_compra_fav)
            val btnCrear = popupView.findViewById<Button>(R.id.btn_crear_lista)
            val btnCerrar = popupView.findViewById<ImageView>(R.id.btn_cerrar_popup)

            btnCerrar.setOnClickListener {
                popup.dismiss()
            }

            btnCrear.setOnClickListener {
                val nombre = nombreLista.text.toString()
                if (nombre.isNotEmpty()) {
                    viewModel.crearListaFavorita(nombre)
                    popup.dismiss()
                } else {
                    nombreLista.error = "El nombre de la lista no puede estar vacío."
                }
            }

            popupBuilder.setView(popupView)
            popup = popupBuilder.create()
            popup.show()
        }
    }

    private fun removerProducto(itemLista: ItemLista) {
        viewModel.removerProductoDeListaById(
            viewModel.getIdListaDeComprasActual(),
            itemLista.producto.id
        )
    }

    private fun clickSumaYResta(producto: ItemLista, cantidad: Int) {
        viewModel.actualizarProductoEnListaById(
            viewModel.getIdListaDeComprasActual(),
            producto.producto.id,
            cantidad
        )
    }

    private fun realizarCompra() {
        //oculto los componentes de la lista de compras
        binding.btnComprasFavoritas.visibility = View.GONE
        binding.btnAgregarListaFav.visibility = View.GONE
        binding.btnRealizarCompra.visibility = View.GONE
        binding.btnCrearLista.visibility = View.GONE
        binding.rvListaCompra.visibility = View.GONE
        binding.txtPrecioTotalLista.visibility = View.GONE

        //muestro los componentes de realizar compra con la checklist
        binding.rvListaRC.visibility = View.VISIBLE
        binding.btnConfirmarCompra.visibility = View.VISIBLE
        binding.btnEditarLista.visibility = View.VISIBLE
        binding.precioTotalCompra.visibility = View.VISIBLE
        binding.txtConfirmarCompra.visibility = View.VISIBLE

        binding.precioTotalCompra.text =
            "Precio total: $" + viewModel.precioTotalListaById(viewModel.getIdListaDeComprasActual())
                .toString()

        binding.rvListaRC.setHasFixedSize(true)
        binding.rvListaRC.layoutManager = LinearLayoutManager(requireContext())

        adapterRC =
            RealizarCompraAdapter(viewModel.getProductosByIdLista(viewModel.getIdListaDeComprasActual()))
        binding.rvListaRC.adapter = adapterRC

        binding.btnConfirmarCompra.setOnClickListener {
            viewModel.realizarCompra()
        }

        /*popupBuilder = AlertDialog.Builder(context)
    val popupView = layoutInflater.inflate(R.layout.popup_realizar_compra, null)
    val reciclerView = popupView.findViewById<RecyclerView>(R.id.rvListaRCOld)
    val btnConfirmar = popupView.findViewById<Button>(R.id.btnConfirmarCompraOld)
    val btnEditarLista = popupView.findViewById<Button>(R.id.btnEditarListaOld)
    val txtPrecioTotal = popupView.findViewById<TextView>(R.id.precioTotalCompraOld)

    txtPrecioTotal.text = "Precio total: $" + viewModel.precioTotalListaById(viewModel.getIdListaDeComprasActual()).toString()

    reciclerView.setHasFixedSize(true)
    reciclerView.layoutManager = LinearLayoutManager(requireContext())

    adapterRC = RealizarCompraAdapter(viewModel.getProductosByIdLista(viewModel.getIdListaDeComprasActual()))
    reciclerView.adapter = adapterRC

    popupBuilder.setView(popupView)
    popup = popupBuilder.create()

    btnConfirmar.setOnClickListener{
        viewModel.realizarCompra()
        popup.dismiss()
    }

    btnEditarLista.setOnClickListener {
        popup.dismiss()
    }

    popup.show()*/
    }

    private fun editarLista() {
        //oculto los componentes de realizar compra con la checklist
        binding.rvListaRC.visibility = View.GONE
        binding.btnConfirmarCompra.visibility = View.GONE
        binding.btnEditarLista.visibility = View.GONE
        binding.precioTotalCompra.visibility = View.GONE
        binding.txtConfirmarCompra.visibility = View.GONE

        //muestro los componentes de la lista de compras
        binding.btnComprasFavoritas.visibility = View.VISIBLE
        binding.btnAgregarListaFav.visibility = View.VISIBLE
        binding.btnRealizarCompra.visibility = View.VISIBLE
        binding.btnCrearLista.visibility = View.VISIBLE
        binding.rvListaCompra.visibility = View.VISIBLE
        binding.txtPrecioTotalLista.visibility = View.VISIBLE


    }

}