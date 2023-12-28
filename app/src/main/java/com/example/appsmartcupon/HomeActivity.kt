package com.example.appsmartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appsmartcupon.databinding.ActivityHomeBinding
import com.example.appsmartcupon.poko.Categoria
import com.example.appsmartcupon.poko.Cliente
import com.example.appsmartcupon.util.Constantes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var cliente: Cliente

    companion object {
        private const val KEY_CLIENTE = "cliente_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val jsonCliente = intent.getStringExtra("cliente")
        if(jsonCliente != null){
            serializarCliente(jsonCliente)
        }

        crearEventos()
    }

    fun crearEventos() {
        binding.btnCliente.setOnClickListener{
            val irCliente = Intent(this@HomeActivity, EditarCuentaActivity::class.java)
            val gson = Gson()
            val cadenaJson: String = gson.toJson(cliente)
            irCliente.putExtra("cliente", cadenaJson)
            startActivity(irCliente)
        }

        binding.btnCategorias.setOnClickListener{
            val activityCategoria = Intent(this@HomeActivity, CategoriasActivity::class.java)
            startActivity(activityCategoria)
        }
        binding.btnListaPromociones.setOnClickListener{
            val irListaPromociones = Intent(this@HomeActivity, ListaPromocionesActivity::class.java)
            startActivity(irListaPromociones)
        }
        binding.btnBusquedaPromociones.setOnClickListener{
            val irBusquedaPromociones = Intent(this@HomeActivity, BusquedaPromocionesActivity::class.java)
            startActivity(irBusquedaPromociones)
        }
    }

    fun serializarCliente(json : String){
        val gson = Gson()
        cliente = gson.fromJson(json, Cliente::class.java)
    }

}