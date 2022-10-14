package com.ort.listapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ort.listapp.R
import com.ort.listapp.data.ProductoRepository
import com.ort.listapp.domain.model.Producto
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var navHostFragment : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomNavView = findViewById(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)

        val repo = ProductoRepository() // Tienen que instanciar el repositorio donde lo quieran usar

        val prs = repo.getProductos() // Después tiene un metodo que les trae los productos

        prs.forEach {
            println(it.imgURL) // Los imgURL son autogenerados por la clase a partir de su id
        }
    }
}