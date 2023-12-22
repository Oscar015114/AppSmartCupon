package com.example.appsmartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appsmartcupon.databinding.ActivityHomeBinding
import com.example.appsmartcupon.poko.Cliente

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnHome.setOnClickListener{
            val irHome = Intent(this@HomeActivity, HomeActivity::class.java)
            startActivity(irHome)
            finish()
        }

        binding.btnCliente.setOnClickListener{
            val irCliente = Intent(this@HomeActivity, EditarCuentaActivity::class.java)
            startActivity(irCliente)
            finish()
        }

        binding.btnCategorias.setOnClickListener{
            val irCategorias = Intent(this@HomeActivity, CategoriasActivity::class.java)
            startActivity(irCategorias)
            finish()
        }
    }
}