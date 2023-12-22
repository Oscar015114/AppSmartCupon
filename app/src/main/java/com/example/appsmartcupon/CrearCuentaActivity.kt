package com.example.appsmartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.appsmartcupon.databinding.ActivityCrearCuentaBinding
import com.example.appsmartcupon.databinding.ActivityLoginBinding
import com.example.appsmartcupon.poko.Cliente
import com.example.appsmartcupon.poko.Mensaje
import com.example.appsmartcupon.poko.RespuestaLogin
import com.example.appsmartcupon.util.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class CrearCuentaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearCuentaBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btCrearCuenta.setOnClickListener {
            enviarDatos()
        }
    }

    private fun enviarDatos() {

        val nombre = binding.etNombre.text.toString()
        val apellidoPaterno = binding.etApellidoPaterno.text.toString()
        val apellidoMaterno = binding.etApellidoMaterno.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val correo = binding.etCorreo.text.toString()
        val calle = binding.etCalle.text.toString()
        val numero = binding.etNumero.text.toString()
        val contrasenia = binding.etContrasenia.text.toString()
        val fechaNacimiento = binding.etFechaNacimiento.text.toString()

        Ion.with(this@CrearCuentaActivity)
            .load("POST", Constantes.URL_WS + "clientes/registrarCliente")
            .setHeader("Content-Type", "application/x-www-form-urlencoded")

            .setBodyParameter("nombre", nombre)
            .setBodyParameter("apellidoPaterno", apellidoPaterno)
            .setBodyParameter("apellidoMaterno", apellidoMaterno)
            .setBodyParameter("telefono", telefono)
            .setBodyParameter("correo", correo)
            .setBodyParameter("calle", calle)
            .setBodyParameter("numero", numero)
            .setBodyParameter("contrasenia", contrasenia)
            .setBodyParameter("fechaNacimiento", fechaNacimiento)
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    validarResultadosPeticiones(result)

                } else {
                    Toast.makeText(
                        this@CrearCuentaActivity,
                        "Por el momento no hay conexion d",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

    }

    private fun validarResultadosPeticiones(json: String) {
        val gson = Gson();
        var respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)
        Toast.makeText(this@CrearCuentaActivity, respuesta.contenido, Toast.LENGTH_LONG).show()
        if (!respuesta.error) {
            val intent = Intent(this@CrearCuentaActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(
                this@CrearCuentaActivity,
                "Hubo algun problema al tratar de registrar un nuevo cliente",
                Toast.LENGTH_LONG
            ).show()
        }
    }


}