package com.example.electoralpartials

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.electoralpartials.databinding.ActivityCadUrnaFotoBinding

private  lateinit var  binding: ActivityCadUrnaFotoBinding

class CadUrnaFoto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadUrnaFotoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonCadVoltarFoto.setOnClickListener {
            startActivity(Intent(this, CadUrna::class.java))

        }


    }
}