package com.example.electoralpartials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.electoralpartials.databinding.ActivityCadUserBinding
import com.example.electoralpartials.databinding.ActivityMainBinding

private  lateinit var  binding: ActivityCadUserBinding
class CadUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        println("Tela cad user!!")
    }
}