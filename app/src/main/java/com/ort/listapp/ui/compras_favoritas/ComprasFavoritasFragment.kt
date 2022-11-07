package com.ort.listapp.ui.compras_favoritas

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.databinding.FragmentComprasFavoritasBinding
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.CompraFavoritaAdapter
import com.ort.listapp.ui.adapters.ProductoAdapter
import com.ort.listapp.ui.adapters.ProductoListadoAdapter

class ComprasFavoritasFragment : Fragment() {

    companion object {
        fun newInstance() = ComprasFavoritasFragment()
    }
    private lateinit var binding: FragmentComprasFavoritasBinding

    private val viewModel: FamilyViewModel by activityViewModels()
    private lateinit var idListaActual: String
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
                GridLayoutManager(requireContext(), 2)
            binding.rvComprasFavoritas.adapter =
                /*viewModel.getFamilia().value?.let { it1 ->
                    viewModel.getListasByTipoEnFamilia(
                        it1,
                        TipoLista.LISTA_FAVORITA
                    )
                }?.let { it2 ->
                    context?.let { it1 ->
                        CompraFavoritaAdapter(
                            it2,
                            it1,
                            onClickLista(lista)
                        )
                    }
                }*/
                viewModel.getFamilia().value?.let { it1 ->
                    viewModel.getListasByTipoEnFamilia(
                        it1,TipoLista.LISTA_FAVORITA)
                }?.let { it2 ->
                    CompraFavoritaAdapter(it2,requireContext()) { lista ->
                        onClickLista(
                            lista
                        )
                    }
                }
           binding.btnVolverListaCompra.setOnClickListener {
               val action = ComprasFavoritasFragmentDirections.actionComprasFavoritasFragmentToListaDeComprasFragment()
               view?.findNavController()?.navigate(action)

           }
        })
    }

    @SuppressLint("SetTextI18n")
    fun onClickLista(lista:Lista){
        idListaActual = lista.id.toString()
        binding.listaFavCompleta.visibility = View.VISIBLE
        binding.txtTotalListaFav.text = "Precio total: $" + idListaActual?.let {
            viewModel.precioTotalListaById(
                it
            )
        }
        binding.nombreListaCF.text = lista.nombre
        binding.rvListaComprasFavoritas.setHasFixedSize(true)
        binding.rvListaComprasFavoritas.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvListaComprasFavoritas.adapter =
            idListaActual?.let { viewModel.getProductosByIdLista(it) }?.let {
                ProductoListadoAdapter(
                    it,
                    requireContext(),
                    {removerProducto(it)},
                    {clickSumaYResta(it, 1)},
                    {clickSumaYResta(it, -1)}
                )
            }
    }
    private fun removerProducto(itemLista: ItemLista) {
        viewModel.removerProductoDeListaById(
            idListaActual,
            itemLista.producto.id
        )
    }

    private fun clickSumaYResta(producto: ItemLista, cantidad: Int) {
        viewModel.actualizarProductoEnListaById(
            idListaActual,
            producto.producto.id,
            cantidad
        )
    }
    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ComprasFavoritasViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}