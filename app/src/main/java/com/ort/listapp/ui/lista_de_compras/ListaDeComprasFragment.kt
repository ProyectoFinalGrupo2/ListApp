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

    private val viewModel: FamilyViewModel by activityViewModels()
    private lateinit var binding: FragmentListaDeComprasBinding

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
        val btnHistorial = binding.btnCrearLista
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

            binding.rvListaRC.setHasFixedSize(true)
            binding.rvListaRC.layoutManager = LinearLayoutManager(requireContext())

            viewModel.cargarChecklist()
            adapterRC =
                RealizarCompraAdapter(viewModel.getProductosChecklist()) { clickChecklist(it) }
            binding.rvListaRC.adapter = adapterRC
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

        btnHistorial.setOnClickListener {
            val action =
                ListaDeComprasFragmentDirections.actionListaDeComprasFragmentToHistorial()
            view?.findNavController()?.navigate(action)
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
        visibilidadListaCompras(View.GONE)

        //muestro los componentes de realizar compra con la checklist
        visibilidadChecklist(View.VISIBLE)

        binding.precioTotalCompra.text =
            "Precio total: $" + viewModel.precioTotalListaById(viewModel.getIdListaDeComprasActual())
                .toString()

        binding.btnConfirmarCompra.setOnClickListener {
            viewModel.realizarCompra()
            editarLista()
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

    private fun clickChecklist(idProducto: String) {
        viewModel.clickChecklistProducto(idProducto)
    }

    private fun editarLista() {
        //oculto los componentes de realizar compra con la checklist
        visibilidadChecklist(View.INVISIBLE)

        //muestro los componentes de la lista de compras
        visibilidadListaCompras(View.VISIBLE)
    }

    private fun visibilidadChecklist(visibility: Int) {
        binding.rvListaRC.visibility = visibility
        binding.btnConfirmarCompra.visibility = visibility
        binding.btnEditarLista.visibility = visibility
        binding.precioTotalCompra.visibility = visibility
        binding.txtConfirmarCompra.visibility = visibility
    }

    private fun visibilidadListaCompras(visibility: Int) {
        binding.btnComprasFavoritas.visibility = visibility
        binding.btnAgregarListaFav.visibility = visibility
        binding.btnRealizarCompra.visibility = visibility
        binding.btnCrearLista.visibility = visibility
        binding.rvListaCompra.visibility = visibility
        binding.txtPrecioTotalLista.visibility = visibility
    }

    //    override fun onDestroyView() {
//        super.onDestroyView()
//        viewModel.vaciarCheckList()
//    }
}
