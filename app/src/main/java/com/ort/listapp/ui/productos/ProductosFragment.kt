package com.ort.listapp.ui.productos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ort.listapp.R
import com.ort.listapp.adapters.ProductoAdapter
import com.ort.listapp.entities.Producto

class ProductosFragment : Fragment() {

    companion object {
        fun newInstance() = ProductosFragment()

    }
    lateinit var v: View

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
        //viewModel.cargarProdsFavs()
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
            listaStock.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg", "https://imagenes.preciosclaros.gob.ar/productos/5410171921991.jpg"))
            i += 1
        }
    }
    fun cargarProdsFav(){
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg", "https://imagenes.preciosclaros.gob.ar/productos/5410171921991.jpg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg", "https://imagenes.preciosclaros.gob.ar/productos/5410171921991.jpg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg", "https://imagenes.preciosclaros.gob.ar/productos/5410171921991.jpg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg", "https://imagenes.preciosclaros.gob.ar/productos/5410171921991.jpg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg", "https://imagenes.preciosclaros.gob.ar/productos/5410171921991.jpg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg", "https://imagenes.preciosclaros.gob.ar/productos/5410171921991.jpg"))
    }
    fun cargarProdsPersonalizados(){
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg", "https://imagenes.preciosclaros.gob.ar/productos/5410171921991.jpg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg", "https://imagenes.preciosclaros.gob.ar/productos/5410171921991.jpg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg", "https://imagenes.preciosclaros.gob.ar/productos/5410171921991.jpg"))
        listaProdsFavs.add(Producto("5410171921991", "01", "0108", "MC CAIN", "Croquetas de Papas Noisettes Mc Cain 1 Kg", 978.0, 997.0, "1.0 kg", "https://imagenes.preciosclaros.gob.ar/productos/5410171921991.jpg"))
    }
}

