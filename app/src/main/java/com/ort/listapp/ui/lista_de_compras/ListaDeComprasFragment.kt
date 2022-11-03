package com.ort.listapp.ui.lista_de_compras

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.adapters.ProductoListadoAdapter
import com.ort.listapp.adapters.RealizarCompraAdapter
import com.ort.listapp.databinding.FragmentListaDeComprasBinding
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel

class ListaDeComprasFragment : Fragment() {

    companion object {
        fun newInstance() = ListaDeComprasFragment()
    }

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
        val btnComprasFavoritas = binding.btnComprasFavoritas

        viewModel.getFamilia().observe(this, Observer {
            binding.listaCompra.setHasFixedSize(true)
            binding.listaCompra.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.listaCompra.adapter =
//                viewModel.getListaByTipo(TipoLista.LISTA_DE_COMPRAS)?.productos?.let {
//                    ProductoListadoAdapter(it, requireContext()){ prod ->
//                        buttonClick(prod)
//                    }
//                }
                ProductoListadoAdapter(
                    viewModel.getProductosByIdLista(viewModel.getIdListaDeComprasActual()),
                    requireContext()
                ) {
                    removerProducto(it)
                }

        })

        btnRealizarCompra.setOnClickListener{
            realizarCompra()
        }
        btnComprasFavoritas.setOnClickListener {
            val action =
                ListaDeComprasFragmentDirections.actionListaDeComprasFragmentToComprasFavoritasFragment()
            view?.findNavController()?.navigate(action)
            //this.findNavController().navigate(action)
        }
    }

    private fun removerProducto(itemLista: ItemLista) {
        viewModel.removerProductoDeListaById(
            viewModel.getIdListaDeComprasActual(),
            itemLista.producto.id
        )
    }



    private fun realizarCompra(){
        popupBuilder = AlertDialog.Builder(context)
        val popupView = layoutInflater.inflate(R.layout.popup_realizar_compra, null)
        val reciclerView = popupView.findViewById<RecyclerView>(R.id.rvListaRC)
        val btnConfirmar = popupView.findViewById<Button>(R.id.btnConfirmarCompra)
        val btnEditarLista = popupView.findViewById<Button>(R.id.btnEditarLista)

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

        popup.show()
    }

}