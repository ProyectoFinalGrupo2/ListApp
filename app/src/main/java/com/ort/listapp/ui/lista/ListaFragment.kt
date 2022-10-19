package com.ort.listapp.ui.lista

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentListaBinding

class ListaFragment : Fragment() {

    companion object {
        fun newInstance() = ListaFragment()
    }

    private lateinit var binding: FragmentListaBinding
    private val viewModel: ListaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }
}