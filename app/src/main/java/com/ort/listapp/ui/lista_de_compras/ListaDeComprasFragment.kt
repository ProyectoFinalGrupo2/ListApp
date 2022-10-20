package com.ort.listapp.ui.lista_de_compras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.adapters.ProductoListadoAdapter
import com.ort.listapp.databinding.FragmentListaDeComprasBinding
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel

class ListaDeComprasFragment : Fragment() {

    companion object {
        fun newInstance() = ListaDeComprasFragment()
    }

    private lateinit var binding: FragmentListaDeComprasBinding

    //    private val viewModel: ListaDeComprasViewModel by activityViewModels()
    private val viewModel: FamilyViewModel by activityViewModels()

    lateinit var listaCompra: RecyclerView


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
                viewModel.getListaByTipo(TipoLista.LISTA_DE_COMPRAS)?.productos?.let { it ->
                    ProductoListadoAdapter(
                        it,
                        requireContext()
                    )
                }
        })
    }

    fun onItemClick(position: Int) {
    }


}