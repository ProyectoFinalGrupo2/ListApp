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
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentComprasFavoritasBinding
import com.ort.listapp.databinding.FragmentProductosBinding
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.CompraFavoritaAdapter
import com.ort.listapp.ui.adapters.ProductoListadoAdapter
import com.ort.listapp.utils.HelperClass

class ComprasFavoritasFragment : Fragment() {

    /*companion object {
        fun newInstance() = ComprasFavoritasFragment()
    }*/
    private var _binding: FragmentComprasFavoritasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FamilyViewModel by activityViewModels()
    private var listaActual: Lista? = null
    private var listas :MutableList<Lista>? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComprasFavoritasBinding.inflate(inflater, container, false)
        cargarListas()
        initRecyclerView()
        return binding.root
    }
    private fun initRecyclerView(){
        binding.rvComprasFavoritas.setHasFixedSize(true)
        binding.rvComprasFavoritas.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvComprasFavoritas.adapter =
            listas?.let { it2 ->
                CompraFavoritaAdapter(it2,requireContext()) { lista ->
                    onClickLista(
                        lista
                    )
                }
            }

        binding.rvListaComprasFavoritas.setHasFixedSize(true)
        binding.rvListaComprasFavoritas.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()

        val rvComprasFavoritas = binding.rvComprasFavoritas
        val btnVolverListaCompra = binding.btnVolverListaCompra

        viewModel.getFamilia().observe(this, Observer {
            listas?.clear()
            viewModel.getFamilia().value?.let { viewModel.getListasByTipoEnFamilia(it,TipoLista.LISTA_FAVORITA) }
                ?.let {listas?.addAll(it) }

            this.actualizarLista()
            rvComprasFavoritas.adapter?.notifyDataSetChanged()

        })
        btnVolverListaCompra.setOnClickListener {
            val action = ComprasFavoritasFragmentDirections.actionComprasFavoritasFragmentToListaDeComprasFragment()
            view?.findNavController()?.navigate(action)
        }

    }


    private fun cargarListas(){
        listas = viewModel.getFamilia().value?.let { viewModel.getListasByTipoEnFamilia(it,TipoLista.LISTA_FAVORITA) }
    }

    private fun onClickLista(lista:Lista){
        listaActual = lista
        this.actualizarLista()
    }


    private fun actualizarLista(){
        if(listaActual != null) {
            val totalLista = listaActual!!.id?.let {
                viewModel.precioTotalListaById(
                    it
                )
            }.toString()
            binding.listaFavCompleta.visibility = View.VISIBLE
            binding.txtTotalListaFav.text = resources.getString(R.string.precio_total,totalLista)
            binding.nombreListaCF.text = listaActual!!.nombre
            //binding.rvListaComprasFavoritas.setHasFixedSize(true)
            //binding.rvListaComprasFavoritas.layoutManager =
            //    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.rvListaComprasFavoritas.adapter =
                listaActual!!.id?.let { viewModel.getProductosByIdLista(it) }?.let { it ->
                    ProductoListadoAdapter(
                        it,
                        requireContext(),
                        { removerProducto(it) },
                        { clickSumaYResta(it, 1) },
                        { clickSumaYResta(it, -1) }
                    )
                }

            binding.btnCopiarListaFav.setOnClickListener {
                listaActual!!.id?.let { it1 -> viewModel.copiarListaFavorita(it1) }
                HelperClass.showToast(requireContext(), "Se pasaron todos los productos a la lista actual")

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

        txtBorrar.text = resources.getString(R.string.borrar_lista,
            "'"+ (listaActual?.nombre +"'"
        ))
        txtDescripcion.text = resources.getString(R.string.aviso_borrar_lista)

        btnBorrar.setOnClickListener {
            viewModel.borrarListaFavorita(listaActual?.id)
            listaActual = null
            binding.listaFavCompleta.visibility = View.INVISIBLE
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
        listaActual?.id?.let {
            viewModel.removerProductoDeListaById(
                it,
                itemLista.producto.id
            )
        }
    }

    private fun clickSumaYResta(producto: ItemLista, cantidad: Int) {
        listaActual?.id?.let {
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}