package com.example.electoralpartials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.electoralpartials.databinding.ActivityCadUrnaBinding

private lateinit var binding: ActivityCadUrnaBinding
class CadUrna : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadUrnaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val estados = arrayOf("Selecione um estado", "Acre", "Bahia")
        val spinnerEstados: Spinner = binding.inputSelectEstados
        geradorListas(estados, spinnerEstados)

        val cidades = arrayOf("Selecione uma cidade", "São Pedro", "Santa Maria")
        val spinnerCidades: Spinner = binding.inputSelectCidades
        geradorListas(cidades, spinnerCidades)

        val presidentes = arrayOf("Selecione um presidente", "Bolsonaro", "Lula")
        val spinnerPresidentes: Spinner = binding.inputSelectPresidente
        geradorListas(presidentes, spinnerPresidentes)


    }
    private fun geradorListas(vetor: Array<String>, spinner: Spinner) {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.style_selects, vetor)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //Toast.makeText(applicationContext, "##### "+ estados[p2], Toast.LENGTH_SHORT)
                println("Opções escolhida de "+ vetor[0] +": "+ vetor[p2])


            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    private fun addEstados(estados: Array<String>){


    }
}