package com.example.appsmartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appsmartcupon.databinding.ActivityEditarCuentaBinding

class EditarCuentaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarCuentaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditarCuentaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnHome.setOnClickListener{
            val irHome = Intent(this@EditarCuentaActivity, HomeActivity::class.java)
            startActivity(irHome)
            finish()
        }

    }
}