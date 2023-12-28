package com.example.appsmartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appsmartcupon.databinding.ActivityEditarCuentaBinding
import com.example.appsmartcupon.poko.Cliente
import com.example.appsmartcupon.poko.Mensaje
import com.example.appsmartcupon.util.Constantes
import com.example.appsmartcupon.util.Utilidades
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

        binding.btnEditarCuenta.setOnClickListener{

            val idCliente = cliente.idCliente
            val nombre = binding.etNombre.text.toString()
            val apellidoPaterno = binding.etApellidoPaterno.text.toString()
            val apellidoMaterno = binding.etApellidoMaterno.text.toString()
            val telefono = binding.etTelefono.text.toString()
            val calle = binding.etCalle.text.toString()
            val numero = binding.etNumero.text.toString()
            val contrasenia = binding.etContrasenia.text.toString()
            val fechaNacimiento = binding.etFechaNacimiento.text.toString()

            if (camposVacios(nombre, apellidoPaterno, apellidoMaterno, telefono, calle, numero, contrasenia, fechaNacimiento)) {
                enviarDatos(idCliente, nombre, apellidoPaterno, apellidoMaterno, telefono, calle, numero, contrasenia, fechaNacimiento)
            }
        }
    }

    fun serializarCliente(json : String){
        val gson = Gson()
        cliente = gson.fromJson(json, Cliente::class.java)
        cargarDatosCliente()
    }

    fun cargarDatosCliente(){
        if(cliente != null) {
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

    fun enviarDatos(
        idCliente: Int, nombre: String, apellidoPaterno: String, apellidoMaterno: String, telefono: String,
        calle: String, numero: String, contrasenia: String, fechaNacimiento: String){

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

    fun validarResultadosPeticiones(json: String) {
        val gson = Gson();
        var respuesta: Mensaje = gson.fromJson(json, Mensaje::class.java)
        Toast.makeText(
            this@EditarCuentaActivity,
            respuesta.contenido,
            Toast.LENGTH_LONG
        ).show()
    }

    fun camposVacios(
        nombre: String, apellidoPaterno: String, apellidoMaterno: String, telefono: String,
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

}