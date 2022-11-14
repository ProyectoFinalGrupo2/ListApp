package com.ort.listapp.ui.infoFamily

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentInfoFamilyBinding

class InfoFamilyFragment : Fragment() {

    private lateinit var binding: FragmentInfoFamilyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoFamilyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        val btnVolver = binding.btnVolverInfoFamily

        btnVolver.setOnClickListener{
            val action = InfoFamilyFragmentDirections.actionInfoFamilyFragmentToListaDeComprasFragment()
            view?.findNavController()?.navigate(action)
        }
    }

}