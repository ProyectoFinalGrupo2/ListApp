package com.ort.listapp.ui.lista_de_compras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ort.listapp.R
import com.ort.listapp.adapters.ProductoListadoAdapter
import com.ort.listapp.databinding.FragmentListaDeComprasBinding
import com.ort.listapp.domain.model.ProductoListado
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel

class ListaDeComprasFragment : Fragment() {

    companion object {
        fun newInstance() = ListaDeComprasFragment()
    }

    private lateinit var binding: FragmentListaDeComprasBinding

    private val viewModel: FamilyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaDeComprasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.getFamilia().observe(this, Observer {
            binding.listaCompra.setHasFixedSize(true)
            binding.listaCompra.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.listaCompra.adapter =
                viewModel.getListaByTipo(TipoLista.LISTA_DE_COMPRAS)?.productos?.let {
                    ProductoListadoAdapter(it, requireContext()){ prod ->
                        onItemClick(prod)
                    }
                }
        })
    }

    fun onItemClick(producto: ProductoListado) {
        val productoListado = getLayoutInflater().inflate(R.layout.fragment_item_lista, null)
        val btnDelete = productoListado.findViewById<Button>(R.id.delete)
        btnDelete.setOnClickListener {
            Snackbar.make(
                binding.root,"Eliminar", Snackbar.LENGTH_SHORT).show()
        }
    }


}