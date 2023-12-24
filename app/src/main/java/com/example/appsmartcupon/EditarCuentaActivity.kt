package com.example.appsmartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appsmartcupon.databinding.ActivityEditarCuentaBinding
import com.example.appsmartcupon.poko.Cliente
import com.example.appsmartcupon.poko.Mensaje
import com.example.appsmartcupon.util.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class EditarCuentaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarCuentaBinding
    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditarCuentaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val jsonCliente = intent.getStringExtra("cliente")

        if(jsonCliente != null){
            serializarCliente(jsonCliente)
            cargarDatosCliente()
        }
        binding.btnHome.setOnClickListener{
            val irHome = Intent(this@EditarCuentaActivity, HomeActivity::class.java)
            startActivity(irHome)
            finish()
        }
        binding.btnEditarCuenta.setOnClickListener{
            enviarDatos()
        }
    }
    private fun serializarCliente(json : String){
        val gson = Gson()
        cliente = gson.fromJson(json, Cliente::class.java)
        cargarDatosCliente()
    }

    private fun cargarDatosCliente(){
        if(cliente != null) {
            title = "Bienvenid@ ${cliente.nombre}"
            binding.etNombre.setText(cliente.nombre)
            binding.etApellidoPaterno.setText(cliente.apellidoPaterno)
            binding.etApellidoMaterno.setText(cliente.apellidoMaterno)
            binding.etTelefono.setText(cliente.telefono)
            binding.etContrasenia.setText(cliente.contrasenia)
            binding.etCalle.setText(cliente.calle)
            binding.etNumero.setText(cliente.numero.toString())
            binding.etFechaNacimiento.setText(cliente.fechaNacimiento)
        }
    }

    private fun enviarDatos(){
        val idCliente = cliente.idCliente
        val nombre = binding.etNombre.text.toString()
        val apellidoPaterno = binding.etApellidoPaterno.text.toString()
        val apellidoMaterno = binding.etApellidoMaterno.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val calle = binding.etCalle.text.toString()
        val numero = binding.etNumero.text.toString()
        val contrasenia = binding.etContrasenia.text.toString()
        val fechaNacimiento = binding.etFechaNacimiento.text.toString()

        Ion.with(this@EditarCuentaActivity)
            .load("PUT", Constantes.URL_WS + "clientes/editarCliente")
            .setHeader("Content-Type", "application/x-www-form-urlencoded")
            .setBodyParameter("idCliente", idCliente.toString())
            .setBodyParameter("nombre", nombre)
            .setBodyParameter("apellidoPaterno", apellidoPaterno)
            .setBodyParameter("apellidoMaterno", apellidoMaterno)
            .setBodyParameter("telefono", telefono)
            .setBodyParameter("calle", calle)
            .setBodyParameter("numero", numero)
            .setBodyParameter("contrasenia", contrasenia)
            .setBodyParameter("fechaNacimiento", fechaNacimiento)
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    validarResultadosPeticiones(result)

                } else {
                    e.printStackTrace()
                    Toast.makeText(
                        this@EditarCuentaActivity,
                        "Por el momento no hay conexion ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun validarResultadosPeticiones(json: String) {
        val gson = Gson();
        var respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)
        Toast.makeText(this@EditarCuentaActivity, respuesta.contenido, Toast.LENGTH_LONG).show()
        if (!respuesta.error) {
            val intent = Intent(this@EditarCuentaActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(
                this@EditarCuentaActivity,
                "Hubo algun problema al tratar de registrar un nuevo cliente",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}