package com.ort.listapp.ui.productos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentItemListaBinding
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.fragments.ItemListaViewModel
import com.ort.listapp.ui.FamilyViewModel

class ItemLista : Fragment() {

    companion object {
        fun newInstance() = ItemLista()
    }

    private var _binding: FragmentItemListaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FamilyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListaBinding.inflate(inflater, container, false)
        val btnDelete = binding.delete
        btnDelete.setOnClickListener {
            viewModel.removerProductoDeListaById(viewModel.getIdListaDeComprasActual(), "0040000017318")
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}