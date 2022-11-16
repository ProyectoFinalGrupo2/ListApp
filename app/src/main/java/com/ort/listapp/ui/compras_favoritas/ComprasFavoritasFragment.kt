package com.ort.listapp.ui.compras_favoritas

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentComprasFavoritasBinding
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.CompraFavoritaAdapter
import com.ort.listapp.ui.adapters.ProductoListadoAdapter
import com.ort.listapp.utils.HelperClass

class ComprasFavoritasFragment : Fragment() {

    private var _binding: FragmentComprasFavoritasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FamilyViewModel by activityViewModels()
    private val comprasViewModel : ComprasFavoritasViewModel by viewModels()
    private lateinit var adapter : CompraFavoritaAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComprasFavoritasBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }
    private fun initRecyclerView(){
        binding.rvComprasFavoritas.setHasFixedSize(true)
        binding.rvComprasFavoritas.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = getListasFavoritas()?.let {
            CompraFavoritaAdapter(it,requireContext()){lista->
                onClickLista(lista)
            }
        }!!
        binding.rvComprasFavoritas.adapter = adapter

        binding.rvListaComprasFavoritas.setHasFixedSize(true)
        binding.rvListaComprasFavoritas.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()

        viewModel.getFamilia().observe(this, Observer {
            getListasFavoritas()?.let { it1 -> adapter.setCompras(it1) }
            this.actualizarLista()
        })

        binding.btnVolverListaCompra.setOnClickListener {
            val action = ComprasFavoritasFragmentDirections.actionComprasFavoritasFragmentToListaDeComprasFragment()
            view?.findNavController()?.navigate(action)
        }

    }

    private fun onClickLista(lista:Lista){
        comprasViewModel.listaActual = lista
        this.actualizarLista()
    }


    private fun actualizarLista(){

        if(comprasViewModel.hayListaSeleccionada()) {
            val listaEnVista = comprasViewModel.listaActual
            val totalLista = listaEnVista!!.id?.let {
                viewModel.precioTotalListaById(
                    it
                )
            }.toString()
            binding.listaFavCompleta.visibility = View.VISIBLE
            binding.txtTotalListaFav.text = resources.getString(R.string.precio_total,totalLista)
            binding.nombreListaCF.text = listaEnVista.nombre
            binding.rvListaComprasFavoritas.adapter =
                listaEnVista.id?.let { viewModel.getProductosByIdLista(it) }?.let { it ->
                    ProductoListadoAdapter(
                        it,
                        requireContext(),
                        { removerProducto(it) },
                        { clickSumaYResta(it, 1) },
                        { clickSumaYResta(it, -1) }
                    )
                }

            binding.btnCopiarListaFav.setOnClickListener {
                listaEnVista.id?.let { it1 -> viewModel.copiarListaFavorita(it1) }
                HelperClass.showToast(requireContext(), comprasViewModel.prodsPasados)

            }
            binding.btnBorrarListaFav.setOnClickListener {
                this.borrarLista()

            }
        }else{
            binding.listaFavCompleta.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun borrarLista(){
        val popupBuilder = AlertDialog.Builder(context)
        val popUpView = layoutInflater.inflate(R.layout.popup_borrar_item, null)
        popupBuilder.setView(popUpView)
        val popUp = popupBuilder.create()
        val btnCancelar = popUpView.findViewById<Button>(R.id.btn_cancelar_borrar)
        val btnBorrar = popUpView.findViewById<Button>(R.id.btn_borrar_item)
        val txtBorrar = popUpView.findViewById<TextView>(R.id.txt_borrar_item)
        val txtDescripcion = popUpView.findViewById<TextView>(R.id.txt_info_borrado_item)
        val btnCerrar = popUpView.findViewById<ImageView>(R.id.btn_cerrar_popup)

        txtBorrar.text = comprasViewModel.getTxtBorrarLista()
        txtDescripcion.text = comprasViewModel.txtAvisoBorrar

        btnBorrar.setOnClickListener {
            viewModel.borrarListaFavorita(comprasViewModel.listaActual?.id)
            comprasViewModel.listaActual = null
            this.actualizarLista()
            popUp.dismiss()
        }
        btnCerrar.setOnClickListener {
            popUp.dismiss()
        }
        btnCancelar.setOnClickListener{
            popUp.dismiss()

        }
        popUp.show()
    }

    private fun removerProducto(itemLista: ItemLista) {
        comprasViewModel.listaActual?.id?.let {
            viewModel.removerProductoDeListaById(
                it,
                itemLista.producto.id
            )
        }
    }

    private fun clickSumaYResta(producto: ItemLista, cantidad: Int) {
        comprasViewModel.listaActual?.id?.let {
            viewModel.actualizarProductoEnListaById(
                it,
                producto.producto.id,
                cantidad
            )
        }
    }
    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ComprasFavoritasViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

    private fun getListasFavoritas() : MutableList<Lista>? {
        return viewModel.getFamilia().value?.let { viewModel.getListasByTipoEnFamilia(it, TipoLista.LISTA_FAVORITA) }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

