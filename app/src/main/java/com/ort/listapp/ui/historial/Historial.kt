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
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var historiales : MutableList<Lista>
    private var ordenReciente = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistorialBinding.inflate(inflater, container, false)
        historiales = mutableListOf()
        cargarHistoriales()
        initRecycler()
        return binding.root
    }

    private fun initRecycler(){
        binding.listaHistoriales.setHasFixedSize(true)
        binding.listaHistoriales.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.listaHistoriales.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            binding.listaHistoriales.adapter = HistorialAdapter(historiales,requireContext()) { historial ->
                onItemClick(historial)
            }

        }
    }

    private fun cargarHistoriales(){
        historiales.clear()
        historiales.addAll(viewModel.getFamilia().value?.let { viewModel.getListasByTipoEnFamilia(it,TipoLista.HISTORIAL) }!!)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        val rvAdapter = binding.listaHistoriales.adapter
        var btnOrdenar = binding.btnOrdenarHistoriales
        viewModel.getFamilia().observe(this, Observer {
            cargarHistoriales()
            rvAdapter?.notifyDataSetChanged()
        })

        binding.btnVolverListaCompra.setOnClickListener {
            val action = HistorialDirections.actionHistorialToListaDeComprasFragment()
            view?.findNavController()?.navigate(action)
        }
        btnOrdenar.setOnClickListener {
                if (!ordenReciente) {
                    historiales.sortByDescending { it.fechaCreacion }
                    btnOrdenar.text = "Ordenar por Antiguos"
                } else {
                    historiales.sortBy { it.fechaCreacion }
                    btnOrdenar.text = "Ordenar por Recientes"

                }
                ordenReciente = !ordenReciente
            binding.listaHistoriales.adapter?.notifyDataSetChanged()
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
        val btnCrear = popUpView.findViewById<Button>(R.id.btn_copiar_historial)

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

        btnCerrar.setOnClickListener {
            popUp.dismiss()
        }

        var id : List<Lista> = ArrayList()

        viewModel.getFamilia().value?.let {
            id = viewModel.getListasByTipoEnFamilia(it, TipoLista.LISTA_DE_COMPRAS)
        }

        btnCrear.setOnClickListener {
            list.productos.forEach { it1 ->
                id.forEach { it2 ->
                    it2.id?.let { it3 -> viewModel.agregarProductoEnListaById(it3, it1) }
                }
            }
            Snackbar.make(
                binding.root, "Se agregaron los productos de la compra hecha el ${list.nombre} a la lista.", Snackbar.LENGTH_SHORT
            ).show()
            popUp.dismiss()
        }

    }
}
