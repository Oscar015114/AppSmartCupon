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
import com.example.appsmartcupon.util.Utilidades
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class CrearCuentaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrearCuentaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        crearEventos()
    }

    fun crearEventos() {

        binding.btCrearCuenta.setOnClickListener {

            val nombre = binding.etNombre.text.toString()
            val apellidoPaterno = binding.etApellidoPaterno.text.toString()
            val apellidoMaterno = binding.etApellidoMaterno.text.toString()
            val telefono = binding.etTelefono.text.toString()
            val correo = binding.etCorreo.text.toString()
            val calle = binding.etCalle.text.toString()
            val numero = binding.etNumero.text.toString()
            val contrasenia = binding.etContrasenia.text.toString()
            val fechaNacimiento = binding.etFechaNacimiento.text.toString()

            if (this.camposVacios(nombre, apellidoPaterno, apellidoMaterno, telefono, correo, calle, numero, contrasenia, fechaNacimiento)) {
                enviarDatos(nombre, apellidoPaterno, apellidoMaterno, telefono, correo, calle, numero, contrasenia, fechaNacimiento)
            }
        }

    }

    fun camposVacios(
        nombre: String, apellidoPaterno: String, apellidoMaterno: String, telefono: String, correo: String,
        calle: String, numero: String, contrasenia: String, fechaNacimiento: String): Boolean {
        var camposValidos = true

        if (nombre.isEmpty()) {
            binding.etNombre.error = Constantes.CAMPOS_OBLIGATORIOS
            camposValidos = false
        }
        if (apellidoPaterno.isEmpty()) {
            binding.etApellidoPaterno.error = Constantes.CAMPOS_OBLIGATORIOS
            camposValidos = false
        }
        if (apellidoMaterno.isEmpty()) {
            binding.etApellidoMaterno.error = Constantes.CAMPOS_OBLIGATORIOS
            camposValidos = false
        }
        if (telefono.isEmpty() || Utilidades.validarCadena(telefono, Utilidades.TELEFONO_REGEX)) {
            binding.etTelefono.error = Constantes.CAMPOS_OBLIGATORIOS
            camposValidos = false
        }
        if (correo.isEmpty() || Utilidades.validarCadena(correo, Utilidades.EMAIL_REGEX)) {
            binding.etCorreo.error = Constantes.CAMPOS_OBLIGATORIOS
            camposValidos = false
        }
        if (calle.isEmpty()) {
            binding.etCalle.error = Constantes.CAMPOS_OBLIGATORIOS
            camposValidos = false
        }
        if (numero.isEmpty() || Utilidades.validarCadena(numero, Utilidades.NUMERO_REGEX)) {
            binding.etNumero.error = Constantes.CAMPOS_OBLIGATORIOS
            camposValidos = false
        }
        if (contrasenia.isEmpty()) {
            binding.etContrasenia.error = Constantes.CAMPOS_OBLIGATORIOS
            camposValidos = false
        }
        if (fechaNacimiento.isEmpty() || Utilidades.validarFecha(fechaNacimiento)) {
            binding.etFechaNacimiento.error = Constantes.CAMPOS_OBLIGATORIOS
            camposValidos = false
        }

        return camposValidos
    }

    fun enviarDatos(
        nombre: String, apellidoPaterno: String, apellidoMaterno: String, telefono: String, correo: String,
        calle: String, numero: String, contrasenia: String, fechaNacimiento: String) {

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
                        "Por el momento no hay conexion. ¡Intentelo mas tarde!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

    }

    fun validarResultadosPeticiones(json: String) {
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