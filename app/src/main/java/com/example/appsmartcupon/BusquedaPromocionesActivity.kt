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
    private var idEmpresa: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusquedaPromocionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvError.visibility = View.GONE

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
                    mostrarInformacionPromocionesPorNombre()
                }else{
                    Toast.makeText(
                        this@BusquedaPromocionesActivity,
                        "Error en la peticion",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    fun serializarInformacionPromocionesPorIdEmpresa(json: String) {
        val gson = Gson()
        val respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)
        if(!respuesta.error){
            promociones = respuesta.promociones
        }
    }

    fun mostrarInformacionPromocionesPorNombre() {
        binding.recyclerPromocionesEmpresa.layoutManager =
            LinearLayoutManager(this@BusquedaPromocionesActivity)

        if (promociones.size > 0) {
            binding.recyclerPromocionesEmpresa.adapter = PromocionBusquedaAdapter(promociones, this)
        } else {
            binding.tvError.visibility = View.VISIBLE
        }
    }

    fun rellenarSpinner(){
    val adapter = ArrayAdapter(this@BusquedaPromocionesActivity,android.R.layout.simple_spinner_item,
    empresas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spBusqueda.adapter = adapter


    }

    fun agregarEventos() {
        binding.btnBusqueda.setOnClickListener {

        }

        binding.spBusqueda.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                val selectedItem = parentView?.getItemAtPosition(position) as Empresa
                 idEmpresa = selectedItem.idEmpresa
                 descargarPromocionesPorIdEmpresa(idEmpresa)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }
    }

    fun obtenerEmpresas(){
        Ion.with(this@BusquedaPromocionesActivity)
            .load(
                "GET",
                "${Constantes.URL_WS}empresas/buscarEmpresas"
            )
            .asString()
            .setCallback { e, result ->

                if (e == null && result != null) {
                    serializarInformacionEmpresas(result)
                    rellenarSpinner()
                }else{
                    Toast.makeText(
                        this@BusquedaPromocionesActivity,
                        "Error en la peticion",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    fun serializarInformacionEmpresas(json: String) {
        val gson = Gson()
        val respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)
        if(!respuesta.error){
            empresas = respuesta.empresas
        }
    }

    override fun clickItemListaPromocion(posicion: Int, promocion: Promocion) {
        val activityDetallePromocion = Intent(this@BusquedaPromocionesActivity,
            DetallePromocionesActivity::class.java)
        activityDetallePromocion.putExtra("idPromocion", promocion.idPromocion.toString())
        startActivity(activityDetallePromocion)
    }
}

