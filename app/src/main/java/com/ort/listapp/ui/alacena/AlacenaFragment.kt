package com.ort.listapp.ui.alacena

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.databinding.FragmentAlacenaBinding
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.AlacenaAdapter
import com.ort.listapp.utils.HelperClass.showToast

class AlacenaFragment : Fragment() {

    private var _binding: FragmentAlacenaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FamilyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlacenaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.getFamilia().observe(this, Observer {
            binding.alacenaProductos.setHasFixedSize(true)
            binding.alacenaProductos.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.alacenaProductos.apply {
                layoutManager = GridLayoutManager(this.context, 2)
            }
            binding.alacenaProductos.adapter =
                AlacenaAdapter(
                    viewModel.getProductosByIdLista(viewModel.getIdAlacenaVirtual()),
                    requireContext(),
                    {
                        clickSumaYResta(it, 1)
                    },
                    {
                        clickSumaYResta(it, -1)
                    })
        })
    }

    private fun clickSumaYResta(producto: ItemLista, cantidad: Int) {
        viewModel.actualizarProductoEnListaById(
            viewModel.getIdAlacenaVirtual(),
            producto.producto.id,
            cantidad
        )
        if(cantidad>0){
            showToast(requireContext(),"Se ha agregado el producto")
        }else{
            showToast(requireContext(),"Se ha quitado el producto")
        }
    }
}