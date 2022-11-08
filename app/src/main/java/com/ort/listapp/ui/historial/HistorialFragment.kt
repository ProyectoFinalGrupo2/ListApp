package com.ort.listapp.ui.historial

import android.annotation.SuppressLint
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
import com.ort.listapp.databinding.FragmentComprasFavoritasBinding
import com.ort.listapp.databinding.FragmentHistorialBinding
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.CompraFavoritaAdapter
import com.ort.listapp.ui.adapters.HistorialAdapter
import com.ort.listapp.ui.compras_favoritas.ComprasFavoritasFragmentDirections

class HistorialFragment : Fragment() {

    companion object {
        fun newInstance() = HistorialFragment()
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
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.listaHistoriales.adapter =
                viewModel.getFamilia().value?.let { it1 ->
                    viewModel.getListasByTipoEnFamilia(
                        it1, TipoLista.HISTORIAL)
                }?.let { it2 ->
                    HistorialAdapter(it2,requireContext()) { lista ->
                        onClickLista(
                            lista
                        )
                    }
                }
            binding.btnVolverListaCompra.setOnClickListener {
                val action = HistorialFragmentDirections.actionHistorialToListaDeComprasFragment()
                view?.findNavController()?.navigate(action)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun onClickLista(lista: Lista){

    }
}