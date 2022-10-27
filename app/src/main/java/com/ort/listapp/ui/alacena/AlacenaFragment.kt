package com.ort.listapp.ui.alacena

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ort.listapp.R
import com.ort.listapp.adapters.AlacenaAdapter
import com.ort.listapp.adapters.ProductoListadoAdapter
import com.ort.listapp.databinding.FragmentAlacenaBinding
import com.ort.listapp.databinding.FragmentListaBinding
import com.ort.listapp.databinding.FragmentProductosBinding
import com.ort.listapp.domain.model.ProductoListado
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel

class AlacenaFragment : Fragment() {

    private var _binding: FragmentAlacenaBinding? = null
    private val binding get() = _binding!!
    private val alacenaViewModel: AlacenaViewModel by viewModels()
    private val viewModel: FamilyViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlacenaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.getFamilia().observe(this, Observer {
            binding.alacenaProductos.setHasFixedSize(true)
            binding.alacenaProductos.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.alacenaProductos.apply {
                layoutManager = GridLayoutManager(this.context, 2)
            }
            binding.alacenaProductos.adapter =
                viewModel.getListaByTipo(TipoLista.ALACENA_VIRTUAL)?.productos?.let {
                    AlacenaAdapter(it, requireContext())
                }
        })
    }
}