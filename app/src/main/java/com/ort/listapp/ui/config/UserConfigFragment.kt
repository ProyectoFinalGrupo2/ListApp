package com.ort.listapp.ui.config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.ort.listapp.databinding.FragmentUserConfigBinding
import com.ort.listapp.ui.infoFamily.InfoFamilyFragmentDirections

class UserConfigFragment : Fragment() {

    private lateinit var binding: FragmentUserConfigBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserConfigBinding.inflate(inflater, container, false)

        return binding.root    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        val btnVolver = binding.btnVolverUserConfig

        btnVolver.setOnClickListener{
            val action = UserConfigFragmentDirections.actionUserConfigFragmentToListaDeComprasFragment()
            view?.findNavController()?.navigate(action)
        }
    }
}