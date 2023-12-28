package com.example.appsmartcupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsmartcupon.databinding.ActivityCategoriasBinding
import com.example.appsmartcupon.interfaces.NotificacionCategoriaLista
import com.example.appsmartcupon.poko.Categoria
import com.example.appsmartcupon.poko.Mensaje
import com.example.appsmartcupon.util.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class CategoriasActivity : AppCompatActivity(), NotificacionCategoriaLista {

    private lateinit var binding: ActivityCategoriasBinding
    private var categorias: ArrayList<Categoria> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        descargarCategorias()
    }

    fun descargarCategorias() {
        Ion.with(this@CategoriasActivity)
            .load("GET", "${Constantes.URL_WS}promociones/buscarCategorias")
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    serializarInformacionCategorias(result)
                    mostrarInformacionCategorias()
                } else {
                    Toast.makeText(
                        this@CategoriasActivity,
                        "Error al obtener las categorias",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    fun serializarInformacionCategorias(json: String) {
        val gson = Gson()
        val respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)
        categorias = respuesta.categorias
    }

    fun mostrarInformacionCategorias() {
        binding.recyclerCategorias.layoutManager = LinearLayoutManager(this@CategoriasActivity)
        // binding.recyclerCategorias.setHasFixedSize(true)
        if (categorias.size > 0) {
            binding.recyclerCategorias.adapter = CategoriasAdapter(categorias, this)
        }
    }

    override fun clickItemListaCategoria(posicion: Int, categoria: Categoria) {
        Toast.makeText(
            this@CategoriasActivity,
            categoria.categoria,
            Toast.LENGTH_LONG
        ).show()
    }
}