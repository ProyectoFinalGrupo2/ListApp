package com.ort.listapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ort.listapp.R
import com.ort.listapp.adapters.ProductoAdapter
import com.ort.listapp.entities.Producto

class Productos : Fragment() {

    companion object {
        fun newInstance() = Productos()

    }
    lateinit var v: View
    lateinit var popUp : AlertDialog
    lateinit var popupBuilder : AlertDialog.Builder
    var listaProdsFavs : MutableList<Producto> = ArrayList<Producto>()
    var listaProdsPersonalizados : MutableList<Producto> = ArrayList<Producto>()
    var listaStock : MutableList<Producto> = ArrayList<Producto>()

    lateinit var recProdsFavoritos:RecyclerView
    lateinit var recProdsPersonalizados:RecyclerView
    lateinit var recStock:RecyclerView
    private lateinit var viewModel: ProductosViewModel
    lateinit var btnFiltrado : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_productos, container, false)
        recProdsFavoritos = v.findViewById(R.id.recViewProductos)
        recProdsPersonalizados = v.findViewById(R.id.listaProdsPersonalizados)
        recStock = v.findViewById(R.id.recListaStock)
        btnFiltrado = v.findViewById(R.id.btnFiltrarProductos)
        cargarStock()
        cargarProdsFav()
        cargarProdsPersonalizados()
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductosViewModel::class.java)
        viewModel.cargarProdsFavs()
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        recProdsFavoritos.setHasFixedSize(true)
        recProdsFavoritos.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
       /* viewModel.cargarProdsFavs().observe(viewLifecycleOwner, Observer{productosFavs->
            recProdsFavoritos.adapter = ProductoAdapter(productosFavs as MutableList<Producto>,requireContext()){ pos->
                onItemClick(pos)
            }

        })*/
        recProdsPersonalizados.setHasFixedSize(true)
        recProdsPersonalizados.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        recStock.setHasFixedSize(true)
        recStock.layoutManager = GridLayoutManager(requireContext(),3)

        recProdsFavoritos.adapter = ProductoAdapter(listaProdsFavs,requireContext()){ pos->
            onItemClick(pos)
        }
        recProdsPersonalizados.adapter =  ProductoAdapter(listaProdsPersonalizados,requireContext()){ pos->
            onItemClick(pos)
        }
        recStock.adapter = ProductoAdapter(listaStock,requireContext()) { pos ->
            onItemClick(pos)
        }

        btnFiltrado.setOnClickListener{

            recProdsFavoritos.layoutManager = GridLayoutManager(requireContext(),3)
        }
    }


    fun onItemClick ( position : Int )  {
        Snackbar.make(v,position.toString(),Snackbar.LENGTH_SHORT).show()
    }

    fun cargarStock(){
        var i = 0
        while(i<15){
            listaStock.add(Producto(i+1,"producto "+i,100.000,null))
            i++
        }
    }
    fun cargarProdsFav(){
        listaProdsFavs.add(Producto(1,"Coca Cola 2,5L", 550.00, "https://arcordiezb2c.vteximg.com.br/arquivos/ids/157821-500-400/GASEOSA-COCA-COLA-PET-1-1808.jpg?v=637432184480130000"))
        listaProdsFavs.add(Producto(2, "Pepitos", 250.00,"https://www.distribuidorapop.com.ar/wp-content/uploads/2017/07/galletitas-pepitos-venta.jpg" ))
        listaProdsFavs.add(Producto(3, "Chocolinas",275.00,"https://jumboargentina.vtexassets.com/arquivos/ids/582951/Galletitas-Chocolinas-250-Gr-1-3431.jpg?v=637234676248800000"))
        listaProdsFavs.add(Producto(4, "Oreos",250.50,"https://ardiaprod.vtexassets.com/arquivos/ids/228248/Galletitas-Oreo-118-Gr-_1.jpg?v=637956480395200000"))

    }
    fun cargarProdsPersonalizados(){
        listaProdsPersonalizados.add(Producto(5,"Lechuga 1Kg", 500.00, null))
        listaProdsPersonalizados.add(Producto(6,"Tomate 1Kg", 350.00, null))
        listaProdsPersonalizados.add(Producto(7,"palta x1", 75.00, null))
        listaProdsPersonalizados.add(Producto(8,"facturas x6", 400.00, null))

    }

    fun crearPopUp(){
        var popUpView : View
        popupBuilder = AlertDialog.Builder(context)
        popUpView = getLayoutInflater().inflate(R.layout.popup_producto_layout,null)


    }
}

