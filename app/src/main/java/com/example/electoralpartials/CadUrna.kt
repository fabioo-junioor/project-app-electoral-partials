package com.example.electoralpartials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.electoralpartials.databinding.ActivityCadUrnaBinding
import com.google.gson.GsonBuilder
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private lateinit var binding: ActivityCadUrnaBinding
/*
private val gson = GsonBuilder().setLenient().create()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl("http://10.0.2.2/electoralPartialsBackEnd/")
    .build()
    .create(CadUrna.buscaEstados::class.java)
*/
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

        val governadores = arrayOf("Selecione um governador", "Carlos", "Pedro")
        val spinnerGovernadores: Spinner = binding.inputSelectGovernador
        geradorListas(governadores, spinnerGovernadores)

        val senadores = arrayOf("Selecione um Senador", "João", "Farias")
        val spinnerSenadores: Spinner = binding.inputSelectSenador
        geradorListas(senadores, spinnerSenadores)


    }
    private fun geradorListas(vetor: Array<String>, spinner: Spinner) {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.style_selects, vetor)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //Toast.makeText(applicationContext, "##### "+ estados[p2], Toast.LENGTH_SHORT)
                println("Opções escolhida: "+ vetor[p2])


            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    private fun mostrarLista(){

    }
}