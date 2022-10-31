package com.ort.listapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ort.listapp.R
import com.ort.listapp.data.FamiliaRepository
import com.ort.listapp.domain.model.Familia
import com.ort.listapp.domain.model.Lista
import com.ort.listapp.domain.model.TipoLista
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomNavView = findViewById(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)

        val familiaRepository = FamiliaRepository()

        val familia = Familia(
            id = "familia4",
            nombre = "Los pORTofino",
            listas = arrayListOf(
                Lista(
                    id = "listaCompras",
                    nombre = "listaCompras: Familia pORTofino",
                    fechaCreacion = "hoy",
                    tipoLista = TipoLista.LISTA_DE_COMPRAS,
                    productos = mutableListOf(
                    )
                ),
                Lista(
                    id = "listaAlacena",
                    nombre = "Alacena Virtual: Familia pORTofino",
                    fechaCreacion = "hoy",
                    tipoLista = TipoLista.ALACENA_VIRTUAL,
                    productos = mutableListOf(
                    )
                )
            ),
            productosFavoritos = arrayListOf(),
            productosPersonalizados = arrayListOf()
        )

        runBlocking {
            withContext(Dispatchers.Default) {
//                familiaRepository.guardarFamilia(familia)
            }
        }

    }
}



