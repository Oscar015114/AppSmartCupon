package com.example.appsmartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appsmartcupon.databinding.ActivityLoginBinding
import com.example.appsmartcupon.poko.Cliente
import com.example.appsmartcupon.poko.RespuestaLogin
import com.example.appsmartcupon.util.Constantes
import com.example.appsmartcupon.util.Utilidades
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        crearEventos()
    }

    fun crearEventos() {

        binding.btIniciarSesion.setOnClickListener{
            val correo = binding.etCorreo.text.toString()
            val contrasenia = binding.etPassword.text.toString()

            if(validarCamposLogin(correo,contrasenia)){
                realizarPeticionLogin(correo, contrasenia)
            }
        }

        binding.btCrearCuenta.setOnClickListener {
            val intent = Intent(this@LoginActivity, CrearCuentaActivity::class.java)
            startActivity(intent)
        }

    }

    fun validarCamposLogin(correo: String, contrasenia: String): Boolean{
        var camposValidos = true

        if(correo.isEmpty() || Utilidades.validarCadena(correo, Utilidades.EMAIL_REGEX)){
            camposValidos = false
            binding.etCorreo.error = Constantes.CAMPOS_OBLIGATORIOS
        }

        if(contrasenia.isEmpty()){
            camposValidos = false
            binding.etPassword.error = Constantes.CAMPOS_OBLIGATORIOS
        }

        return camposValidos
    }

    fun realizarPeticionLogin(correo: String, contrasenia: String){
        Ion.getDefault(this@LoginActivity).conscryptMiddleware.enable(false)
        Ion.with(this@LoginActivity).load("POST", Constantes.URL_WS + "autenticacion/iniciarSesionMobile")
            .setHeader("Content-Type","application/x-www-form-urlencoded")
            .setBodyParameter("correo", correo).setBodyParameter("contrasenia", contrasenia).asString()
            .setCallback{ e, result ->
                if(e == null && result != null){
                 serializarRespuestaLogin(result)
                }else{
                    Toast.makeText(
                        this@LoginActivity,
                        "Error en la peticion, porfavor intentelo mas tarde",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    fun serializarRespuestaLogin(json: String){
        val gson = Gson()
        var respuesta: RespuestaLogin = gson.fromJson(json, RespuestaLogin::class.java)

        Toast.makeText(this@LoginActivity, respuesta.contenido, Toast.LENGTH_LONG).show()

        if(!respuesta.error){
          irPantallaPrincipal(respuesta.cliente)
        }
    }

    fun irPantallaPrincipal(cliente : Cliente){
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        val gson = Gson()
        val cadenaJson: String = gson.toJson(cliente)
        intent.putExtra("cliente", cadenaJson)

        startActivity(intent)
        finish()
    }
}