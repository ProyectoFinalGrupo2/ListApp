package com.ort.listapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ort.listapp.R
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.ProductoListado
import com.ort.listapp.domain.model.TipoLista
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomNavView = findViewById(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)

        val repo =
            ProductoRepository() // Tienen que instanciar el repositorio donde lo quieran usar

        val prs = repo.getProductos() // Después tiene un metodo que les trae los productos

        runBlocking {
            launch {
                val listOfProductsId = listOf(
                    "5410171921991",
                    "0040000017318",
                    "7790250047162",
                    "0080432400432",
                    "7790895007057",
                    "7790742656018",
                    "0000075024956",
                    "7891000244111",
                    "0000075024956",
                    "4058075498051",
                )
                val prs2 = repo.getProductosByListaIds(listOfProductsId)
                prs2.forEach {
                    println(it.toString()) // Los imgURL son autogenerados por la clase a partir de su id
                }
            }
        }

        val listaCompras = Lista(
            "pruebaListaCompras",
            "pruebaListaCompras",
            "2022/05/05",
            TipoLista.LISTA_DE_COMPRAS,
            listOf(
                ProductoListado(1, "usuarioId", "5410171921991"),
                ProductoListado(2, "usuarioId", "0040000017318"),
                ProductoListado(3, "usuarioId", "7790250047162"),
                ProductoListado(4, "usuarioId", "0080432400432")
            )
        )

    }
}


