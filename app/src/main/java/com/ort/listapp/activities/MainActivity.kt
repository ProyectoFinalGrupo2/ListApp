package com.ort.listapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ort.listapp.R
import com.ort.listapp.data.ProductoRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repo = ProductoRepository() // Tienen que instanciar el repositorio donde lo quieran usar

        val prs = repo.getProductos() // Después tiene un metodo que les trae los productos

        prs.forEach {
            println(it.imgURL) // Los imgURL son autogenerados por la clase a partir de su id
        }
    }
}