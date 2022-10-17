package com.ort.listapp.ui.lista_de_compras

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.listapp.R
import com.ort.listapp.adapters.ProductoListadoAdapter
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.ProductoListado

class ListaDeComprasFragment : Fragment() {

    companion object {
        fun newInstance() = ListaDeComprasFragment()
    }

    lateinit var v: View

    lateinit var listaCompra: RecyclerView

    val repo = ProductoRepository()

    val prs = repo.getProductos()

    var productos : MutableList<ProductoListado> = ArrayList<ProductoListado>()

    private lateinit var viewModel: ListaDeComprasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_lista_de_compras, container, false)
        listaCompra = v.findViewById(R.id.listaCompra)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListaDeComprasViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        cargarProds()
        listaCompra.setHasFixedSize(true)
        listaCompra.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)

        listaCompra.adapter = ProductoListadoAdapter(productos, requireContext())
    }

    fun onItemClick ( position : Int )  {
    }

    fun cargarProds(){
        productos.add(ProductoListado(4, "Juan", "4058075498051"))
        productos.add(ProductoListado(2, "Candela", "7891000244111"))
        productos.add(ProductoListado(8, "Valentino", "0000075024956"))
        productos.add(ProductoListado(2, "Candela", "7790742656018"))
        productos.add(ProductoListado(1, "Rafael", "7790895007057"))
        productos.add(ProductoListado(4, "Valentino", "0080432400432"))
        productos.add(ProductoListado(3, "Valentino", "7790250047162"))
        productos.add(ProductoListado(1, "Martin", "0040000017318"))
        productos.add(ProductoListado(3, "Martin", "5410171921991"))
    }
}