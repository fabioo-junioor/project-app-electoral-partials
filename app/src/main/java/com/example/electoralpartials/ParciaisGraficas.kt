package com.example.electoralpartials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.electoralpartials.databinding.ActivityCadUrnaBinding
import com.example.electoralpartials.databinding.ActivityParciaisGraficasBinding

private lateinit var binding: ActivityParciaisGraficasBinding
class ParciaisGraficas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParciaisGraficasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.button_add_nav -> {
                    val cadUrna = Intent(this, CadUrna::class.java)
                    println("MUDOU PARA TELA CAD URNA!")
                    startActivity(cadUrna)
                    true
                }
                else -> {
                    false

                }
            }
        }
    }
}