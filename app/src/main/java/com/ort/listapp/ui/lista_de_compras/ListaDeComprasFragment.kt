package com.ort.listapp.ui.lista_de_compras

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.adapters.ProductoListadoAdapter
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.databinding.FragmentListaBinding
import com.ort.listapp.databinding.FragmentListaDeComprasBinding
import com.ort.listapp.domain.model.ProductoListado

class ListaDeComprasFragment : Fragment() {

    companion object {
        fun newInstance() = ListaDeComprasFragment()
    }

    private lateinit var binding: FragmentListaDeComprasBinding
    private val viewModel: ListaDeComprasViewModel by viewModels()

    lateinit var v: View

    lateinit var listaCompra: RecyclerView

    val repo = ProductoRepository()

    val prs = repo.getProductos()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaDeComprasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.cargarProds()
        binding.listaCompra.setHasFixedSize(true)
        binding.listaCompra.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)

        binding.listaCompra.adapter = ProductoListadoAdapter(viewModel.listaDeCompras.productos, requireContext())
    }

    fun onItemClick ( position : Int )  {
    }


}