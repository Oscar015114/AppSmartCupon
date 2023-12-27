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
    var listaCategorias = ArrayList<Categoria>()
    var recyclerview : RecyclerView? = null

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

        descargarCategorias()
        recyclerview = findViewById<RecyclerView>(R.id.recycler_categorias)

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
            finish()
        }

        binding.btnCategorias.setOnClickListener{
            val irCategorias = Intent(this@HomeActivity, CategoriasActivity::class.java)
            val gson = Gson()
            val cadenaJson: String = gson.toJson(listaCategorias)
            irCategorias.putExtra("categorias", cadenaJson)
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

    private fun descargarCategorias(){
        Ion.with(this@HomeActivity).load("GET",Constantes.URL_WS+"promociones/buscarCategorias")
            //aca arriba deberia ir "categorias/buscarCategorias" para que me pueda dar el id y nombre de la categoria
                //y asi tambien poder usar lo de listaCategorias, ya que si no tendria que ser listaPromociones, pero aca no podria
                //conseguir el nombre de la categoria para usarlo en el adapter
            .asString()
            .setCallback { e, result ->
                if(e == null && result != null){
                   convertirJsonCategoria(result)
                }else{
                    Toast.makeText(
                        this@HomeActivity,
                        "Error en la peticion, porfavor intentelo mas tarde",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        
    }

    private fun convertirJsonCategoria(json: String){
        val gson = Gson()
        val typeCat = object : TypeToken<ArrayList<Categoria>>(){}.type
        listaCategorias = gson.fromJson(json, typeCat)
        cargarCategorias()
    }

    private fun cargarCategorias(){
        val adaptador = CategoriasAdapter()
        adaptador.context = this@HomeActivity
        adaptador.categorias = listaCategorias
        recyclerview?.layoutManager = LinearLayoutManager(this)
        recyclerview?.adapter = adaptador
    }

}