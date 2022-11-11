package com.ort.listapp.ui.historial

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentHistorialBinding
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.HistorialAdapter
import com.google.firebase.Timestamp
import com.ort.listapp.ui.adapters.ProductoListadoAdapter
import com.ort.listapp.ui.adapters.ProductoListadoHistorialAdapter
import com.ort.listapp.ui.productos.ProductosViewModel
import java.time.Instant.now
import java.time.LocalDate

class Historial : Fragment() {

    companion object {
        fun newInstance() = Historial()
    }

    private lateinit var binding: FragmentHistorialBinding
    private val viewModel: FamilyViewModel by activityViewModels()
    private lateinit var popUp: AlertDialog
    private lateinit var popupBuilder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        var hist : List<Lista> = ArrayList()
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
                    hist = it.sortedBy {
                        it.fechaCreacion
                    }
                    HistorialAdapter(hist, requireContext()) {
                        onItemClick(it)
                    }
                }
        })


        binding.btnVolverListaCompra.setOnClickListener {
            val action = HistorialDirections.actionHistorialToListaDeComprasFragment()
            view?.findNavController()?.navigate(action)
        }

    }

    @SuppressLint("SetTextI18n")
    fun onItemClick(list : Lista){
        popupBuilder = AlertDialog.Builder(context)
        val popUpView = layoutInflater.inflate(R.layout.popup_historial, null)
        val titulo = popUpView.findViewById<TextView>(R.id.txt_nom_prod_popup)
        val precio = popUpView.findViewById<TextView>(R.id.txt_cantidad_agregarprod)
        val prods = popUpView.findViewById<RecyclerView>(R.id.prods)
        val btnCerrar = popUpView.findViewById<ImageView>(R.id.btn_cerrar_popup)
        val btnCrear = popUpView.findViewById<Button>(R.id.btn_crear_lista)

        popupBuilder.setView(popUpView)
        popUp = popupBuilder.create()

        popUp.show()

        titulo.text = "Compra realizada el ${list.nombre}"
        precio.text = "Precio total: $${list.id?.let { viewModel.precioTotalListaById(it) }}"
        prods.setHasFixedSize(true)
        prods.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        prods.adapter = ProductoListadoHistorialAdapter(
            list.productos, requireContext()
        )
    }
}
