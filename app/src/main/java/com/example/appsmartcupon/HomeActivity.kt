package com.example.appsmartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appsmartcupon.databinding.ActivityHomeBinding
import com.example.appsmartcupon.poko.Cliente
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val jsonCliente = intent.getStringExtra("cliente")
        if(jsonCliente != null){
            serializarCliente(jsonCliente)
        }

        binding.tvUsuario.setText("Hola"+cliente.nombre+"Encuentra las mejores promociones")

        binding.btnHome.setOnClickListener{
            val irHome = Intent(this@HomeActivity, HomeActivity::class.java)
            startActivity(irHome)
            finish()
        }

        binding.btnCliente.setOnClickListener{
            val irCliente = Intent(this@HomeActivity, EditarCuentaActivity::class.java)
            val gson = Gson()
            val cadenaJson: String = gson.toJson(cliente)
            irCliente.putExtra("cliente", cadenaJson)
            startActivity(irCliente)
            //finish()
        }

        binding.btnCategorias.setOnClickListener{
            val irCategorias = Intent(this@HomeActivity, CategoriasActivity::class.java)
            startActivity(irCategorias)
            finish()
        }
        binding.btnListaPromociones.setOnClickListener{
            val irListaPromociones = Intent(this@HomeActivity, ListaPromocionesActivity::class.java)
            startActivity(irListaPromociones)
            finish()
        }
        binding.btnBusquedaPromociones.setOnClickListener{
            val irBusquedaPromociones = Intent(this@HomeActivity, BusquedaPromocionesActivity::class.java)
            startActivity(irBusquedaPromociones)
            finish()
        }
    }

    private fun serializarCliente(json : String){
        val gson = Gson()
        cliente = gson.fromJson(json, Cliente::class.java)
    }

}