package com.example.appsmartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appsmartcupon.databinding.ActivityCategoriasBinding

class CategoriasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnHome.setOnClickListener{
            val irHome = Intent(this@CategoriasActivity, HomeActivity::class.java)
            startActivity(irHome)
            finish()
        }

    }
}