package com.example.appsmartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsmartcupon.databinding.ActivityListaPromocionesBinding
import com.example.appsmartcupon.interfaces.NotificacionPromocionLista
import com.example.appsmartcupon.poko.Mensaje
import com.example.appsmartcupon.poko.Promocion
import com.example.appsmartcupon.util.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class ListaPromocionesActivity : AppCompatActivity(), NotificacionPromocionLista {

    private lateinit var binding: ActivityListaPromocionesBinding
    private var promociones: ArrayList<Promocion> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaPromocionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvError.visibility = View.GONE

        val idCategoria = intent.getStringExtra("idCategoria")
        if (idCategoria != null) {
            descargarPromociones(idCategoria.toInt())
        }

    }

    fun descargarPromociones(idCategoria: Int) {
        Ion.with(this@ListaPromocionesActivity)
            .load(
                "GET",
                "${Constantes.URL_WS}promociones/buscarPromocionesPorCategoria/${idCategoria}"
            )
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    serializarInformacionPromociones(result)
                    mostrarInformacionPromociones()
                }
            }
    }

    fun serializarInformacionPromociones(json: String) {
        val gson = Gson()
        val respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)
        promociones = respuesta.promociones
    }

    fun mostrarInformacionPromociones() {
        binding.recyclerPromociones.layoutManager =
            LinearLayoutManager(this@ListaPromocionesActivity)

        if (promociones.size > 0) {
            binding.recyclerPromociones.adapter = PromocionesAdapter(promociones, this)
        } else {
            binding.tvError.visibility = View.VISIBLE
        }
    }

    override fun clickItemListaPromocion(posicion: Int, promocion: Promocion) {
        val activityDetallePromocion = Intent(this@ListaPromocionesActivity,
            DetallePromocionesActivity::class.java)
        activityDetallePromocion.putExtra("idPromocion", promocion.idPromocion.toString())
        startActivity(activityDetallePromocion)
    }

}