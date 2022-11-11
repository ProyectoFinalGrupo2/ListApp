package com.ort.listapp.ui.historial

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentHistorialBinding
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.HistorialAdapter
import com.google.firebase.Timestamp
import java.time.Instant.now
import java.time.LocalDate

class Historial : Fragment() {

    companion object {
        fun newInstance() = Historial()
    }

    private lateinit var binding: FragmentHistorialBinding
    private val viewModel: FamilyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFamilia().observe(this, Observer {
            binding.listaHistoriales.setHasFixedSize(true)
            binding.listaHistoriales.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.listaHistoriales.apply {
                layoutManager = GridLayoutManager(this.context, 2)
            }
            binding.listaHistoriales.adapter =
                viewModel.getFamilia().value?.let {
                    viewModel.getListasByTipoEnFamilia(
                        it, TipoLista.HISTORIAL
                    )
                }?.let {
                    HistorialAdapter(it, requireContext()) {

                    }
                }
        })


        binding.btnVolverListaCompra.setOnClickListener {
            val action = HistorialDirections.actionHistorialToListaDeComprasFragment()
            view?.findNavController()?.navigate(action)
        }

    }
}
