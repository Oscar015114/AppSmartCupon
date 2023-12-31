package com.example.appsmartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsmartcupon.databinding.ActivityBusquedaPromocionesBinding
import com.example.appsmartcupon.interfaces.NotificacionPromocionLista
import com.example.appsmartcupon.poko.Empresa
import com.example.appsmartcupon.poko.Mensaje
import com.example.appsmartcupon.poko.Promocion
import com.example.appsmartcupon.util.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class BusquedaPromocionesActivity : AppCompatActivity(), NotificacionPromocionLista {

    private lateinit var binding: ActivityBusquedaPromocionesBinding
    private var promociones: ArrayList<Promocion> = ArrayList()
    private var empresas: ArrayList<Empresa> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusquedaPromocionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        agregarEventos()
        obtenerEmpresas()

    }

    fun descargarPromocionesPorIdEmpresa(idEmpresa: Int) {
        Ion.with(this@BusquedaPromocionesActivity)
            .load(
                "GET",
                "${Constantes.URL_WS}promociones/buscarPromocionesPorIdEmpresa/${idEmpresa}"
            )
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    serializarInformacionPromocionesPorIdEmpresa(result)
                }
            }
    }

    fun obtenerEmpresas() {
        Ion.with(this@BusquedaPromocionesActivity)
            .load(
                "GET",
                "${Constantes.URL_WS}empresas/buscarEmpresas"
            )
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    serializarInformacionEmpresas(result)
                }
            }
    }

    fun serializarInformacionEmpresas(json: String) {
        val gson = Gson()
        val respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)
        if (!respuesta.error) {
            empresas = respuesta.empresas
            rellenarSpinner()
        }
    }

    fun serializarInformacionPromocionesPorIdEmpresa(json: String) {
        val gson = Gson()
        val respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)
        promociones = respuesta.promociones
        mostrarInformacionPromocionesPorNombre()
    }

    override fun clickItemListaPromocion(posicion: Int, promocion: Promocion) {
        val activityDetallePromocion = Intent(
            this@BusquedaPromocionesActivity,
            DetallePromocionesActivity::class.java
        )
        activityDetallePromocion.putExtra("idPromocion", promocion.idPromocion.toString())
        startActivity(activityDetallePromocion)
    }

    fun mostrarInformacionPromocionesPorNombre() {
        binding.recyclerPromocionesEmpresa.layoutManager =
            LinearLayoutManager(this@BusquedaPromocionesActivity)

        binding.tvError.visibility = View.GONE
        binding.recyclerPromocionesEmpresa.adapter = PromocionBusquedaAdapter(promociones, this)

        if (promociones.size <= 0) {
            binding.tvError.visibility = View.VISIBLE
        }
    }

    fun rellenarSpinner() {
        val adapter = ArrayAdapter(
            this@BusquedaPromocionesActivity, android.R.layout.simple_spinner_item,
            empresas
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spBusqueda.adapter = adapter
    }

    fun agregarEventos() {
        binding.btnBusqueda.setOnClickListener {

        }

        binding.spBusqueda.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parentView?.getItemAtPosition(position) as Empresa
                descargarPromocionesPorIdEmpresa(selectedItem.idEmpresa)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }
    }

}

