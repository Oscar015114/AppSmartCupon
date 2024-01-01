package com.example.appsmartcupon

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsmartcupon.databinding.ActivityDetallePromocionesBinding
import com.example.appsmartcupon.poko.Mensaje
import com.example.appsmartcupon.poko.Promocion
import com.example.appsmartcupon.poko.Sucursal
import com.example.appsmartcupon.util.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class DetallePromocionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetallePromocionesBinding
    private var promocion: Promocion = Promocion()
    private var sucursales: ArrayList<Sucursal> = ArrayList()
    private var fotografia: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePromocionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val idPromocion = intent.getStringExtra("idPromocion")
        if (idPromocion != null) {
            descargarFotografia(idPromocion.toInt())
            descargarInformacionPromocion(idPromocion.toInt())
            descargarSucursalesValidas(idPromocion.toInt())
        }

    }

    fun descargarFotografia(idPromocion: Int) {
        Ion.with(this@DetallePromocionesActivity)
            .load("GET", "${Constantes.URL_WS}promociones/buscarFotografia/${idPromocion}")
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    serializarFotografiaPromocion(result)
                }
            }
    }

    fun descargarInformacionPromocion(idPromocion: Int) {
        Ion.with(this@DetallePromocionesActivity)
            .load("GET", "${Constantes.URL_WS}promociones/buscarPromocion/${idPromocion}")
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    serializarInformacionPromocion(result)
                }
            }
    }

    fun descargarSucursalesValidas(idPromocion: Int) {
        Ion.with(this@DetallePromocionesActivity)
            .load("GET", "${Constantes.URL_WS}promociones/buscarSucursalesValidas/${idPromocion}")
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    serializarSucursalesValidas(result)
                }
            }
    }

    fun serializarFotografiaPromocion(json: String) {
        val gson = Gson()
        val respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)
        if (!respuesta.error) {
            fotografia = respuesta.promocion.fotografiaBase64
            decodificarFotografia()
        }
    }

    fun serializarInformacionPromocion(json: String) {
        val gson = Gson()
        val respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)

        if (!respuesta.error) {
            promocion = respuesta.promocion
            cargarInformacionPromocion()
        }
    }

    fun serializarSucursalesValidas(json: String) {
        val gson = Gson()
        val respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)
        if (!respuesta.error) {
            sucursales = respuesta.sucursales
            cargarSucursalesValidas()
        }
    }

    fun decodificarFotografia() {
        val decodedBytes = android.util.Base64.decode(fotografia, android.util.Base64.DEFAULT)
        val foto = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        binding.ivFotografiaDetallePromocion.setImageBitmap(foto)
    }

    fun cargarInformacionPromocion() {
        binding.tvNombreDetallePromocion.text = promocion.nombre
        binding.tvTipoPromocionDetallePromocion.text = promocion.tipoPromocion
        binding.tvCuponesDetallePromocion.text = promocion.noCuponesMaximo.toString()
        binding.tvCodigoDetallePromocion.text = promocion.codigo
        binding.tvDescripcionDetallePromocion.text = promocion.descripcion
        binding.tvRestriccionDetallePromocion.text = promocion.restriccion
        binding.tvFechaInicioDetallePromocion.text = "Inicio: ${promocion.fechaInicio}"
        binding.tvFechaFinDetallePromocion.text = "Fin: ${promocion.fechaFin}"

        if (promocion.tipoPromocion == Constantes.PROMOCION_REBAJADO) {
            binding.tvPorcentajeDescuentoDetallePromocion.text =
                "${promocion.precioRebajado} pesos de rebaja"
        } else {
            binding.tvPorcentajeDescuentoDetallePromocion.text =
                "${promocion.porcentajeDescuento}% de descuento"
        }
    }

    fun cargarSucursalesValidas() {
        binding.recyclerSucursales.layoutManager =
            LinearLayoutManager(this@DetallePromocionesActivity)
        if (sucursales.size > 0) {
            binding.recyclerSucursales.adapter = SucursalesAdapter(sucursales)
        }
    }

}