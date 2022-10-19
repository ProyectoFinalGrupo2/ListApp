package com.ort.listapp.ui.lista_de_compras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.adapters.ProductoListadoAdapter
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.databinding.FragmentListaDeComprasBinding

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



        viewModel.listaDeCompras.observe(this, Observer {
            binding.listaCompra.setHasFixedSize(true)
            binding.listaCompra.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.listaCompra.adapter =
                viewModel.listaDeCompras.value?.let {
                    ProductoListadoAdapter(
                        it.productos,
                        requireContext()
                    )
                }
        })
    }

    fun onItemClick(position: Int) {
    }


}