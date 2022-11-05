package com.ort.listapp.ui.compras_favoritas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentComprasFavoritasBinding
import com.ort.listapp.databinding.FragmentListaDeComprasBinding
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.CompraFavoritaAdapter
import com.ort.listapp.ui.adapters.ProductoListadoAdapter

class ComprasFavoritasFragment : Fragment() {

    companion object {
        fun newInstance() = ComprasFavoritasFragment()
    }
    private lateinit var binding: FragmentComprasFavoritasBinding

    private val viewModel: FamilyViewModel by activityViewModels()
//    private lateinit var viewModel: ComprasFavoritasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComprasFavoritasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFamilia().observe(this, Observer {
            binding.rvComprasFavoritas.setHasFixedSize(true)
            binding.rvComprasFavoritas.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.rvComprasFavoritas.adapter =
                viewModel.getFamilia().value?.let { it1 ->
                    viewModel.getListaByTipoEnFamilia(
                        it1,
                        TipoLista.LISTA_FAVORITA
                    )
                }?.let { it2 ->
                    context?.let { it1 ->
                        CompraFavoritaAdapter(
                            it2,
                            it1
                        )
                    }
                }
        })
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ComprasFavoritasViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}