package com.ort.listapp.ui.lista_de_compras

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentListaDeComprasBinding
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.ProductoListadoAdapter
import com.ort.listapp.ui.adapters.RealizarCompraAdapter

class ListaDeComprasFragment : Fragment() {

//    companion object {
//        fun newInstance() = ListaDeComprasFragment()
//    }

    private lateinit var binding: FragmentListaDeComprasBinding

    private val viewModel: FamilyViewModel by activityViewModels()

    lateinit var popup: AlertDialog
    lateinit var popupBuilder: AlertDialog.Builder
    lateinit var adapterRC: RealizarCompraAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        viewModel.getFamilia().observe(this, Observer {
            binding.txtPrecioTotalLista.text =
                "Precio total: $" + viewModel.precioTotalListaById(viewModel.getIdListaDeComprasActual())
                    .toString()
            binding.listaCompra.setHasFixedSize(true)
            binding.listaCompra.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.listaCompra.adapter =
                ProductoListadoAdapter(
                    viewModel.getProductosByIdLista(viewModel.getIdListaDeComprasActual()),
                    requireContext(),
                    { removerProducto(it) },
                    { clickSumaYResta(it, 1) },
                    { clickSumaYResta(it, -1) }
                )

            binding.rvListaRC.setHasFixedSize(true)
            binding.rvListaRC.layoutManager = LinearLayoutManager(requireContext())

            viewModel.cargarChecklist()
            adapterRC = RealizarCompraAdapter(viewModel.getProductosChecklist()) { clickChecklist(it) }
            binding.rvListaRC.adapter = adapterRC
        })

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
            //this.findNavController().navigate(action)
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
        binding.btnComprasFavoritas.visibility = View.INVISIBLE
        binding.btnAgregarListaFav.visibility = View.INVISIBLE
        binding.btnRealizarCompra.visibility = View.INVISIBLE
        binding.btnCrearLista.visibility = View.INVISIBLE
        binding.listaCompra.visibility = View.INVISIBLE
        binding.txtPrecioTotalLista.visibility = View.INVISIBLE

        //muestro los componentes de realizar compra con la checklist
        binding.rvListaRC.visibility = View.VISIBLE
        binding.btnConfirmarCompra.visibility = View.VISIBLE
        binding.btnEditarLista.visibility = View.VISIBLE
        binding.precioTotalCompra.visibility = View.VISIBLE
        binding.txtConfirmarCompra.visibility = View.VISIBLE

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

    private fun clickChecklist(idProducto: String){
        viewModel.clickChecklistProducto(idProducto)
    }

    private fun editarLista() {
        //oculto los componentes de realizar compra con la checklist
        binding.rvListaRC.visibility = View.INVISIBLE
        binding.btnConfirmarCompra.visibility = View.INVISIBLE
        binding.btnEditarLista.visibility = View.INVISIBLE
        binding.precioTotalCompra.visibility = View.INVISIBLE
        binding.txtConfirmarCompra.visibility = View.INVISIBLE

        //muestro los componentes de la lista de compras
        binding.btnComprasFavoritas.visibility = View.VISIBLE
        binding.btnAgregarListaFav.visibility = View.VISIBLE
        binding.btnRealizarCompra.visibility = View.VISIBLE
        binding.btnCrearLista.visibility = View.VISIBLE
        binding.listaCompra.visibility = View.VISIBLE
        binding.txtPrecioTotalLista.visibility = View.VISIBLE

    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        viewModel.vaciarCheckList()
//    }
}