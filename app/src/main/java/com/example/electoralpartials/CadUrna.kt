package com.example.electoralpartials

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.electoralpartials.databinding.ActivityCadUrnaBinding
import com.example.electoralpartials.model.Candidato
import com.example.electoralpartials.model.Cidade
import com.example.electoralpartials.model.Estado
import org.json.JSONArray
import org.json.JSONException
import java.text.Normalizer
import java.util.*
import kotlin.concurrent.schedule

private lateinit var binding: ActivityCadUrnaBinding
val url = "http://10.0.2.2/electoralPartialsBackEnd"

var estadoEscolhido: String = ""
var cidadeEscolhida: String = ""
var presidenteEscolhido: String = ""
var zonaEscolhida: String = ""
var secaoEscolhida: String = ""

class CadUrna : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadUrnaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val estados = mutableListOf<String>()
        mostrarListaEstados(estados)


        val presidentes = mutableListOf<String>()
        val spinnerPresidentes: Spinner = binding.inputSelectPresidente
        mostrarListaCandidatos(presidentes, spinnerPresidentes, 1)

        binding.buttonCadUrnaSalvar.setOnClickListener{
            val numZona = binding.inputCadUrnaZona.text.toString()
            val numSecao = binding.inputCadUrnaSessao.text.toString()
            val totalVotos = binding.inputCadUrnaTotal.text.toString()
            val votosBrancos = binding.inputCadUrnaBrancos.text.toString()
            val votosNulos = binding.inputCadUrnaNulos.text.toString()

            if(TextUtils.isEmpty(numSecao)){
                Toast.makeText(this, "Preencha o campo Seçao!", Toast.LENGTH_SHORT).show()

            }else if(TextUtils.isEmpty(numZona)) {
                Toast.makeText(this, "Preencha o campo Zona!", Toast.LENGTH_SHORT).show()

            }else{
                if(!(TextUtils.isEmpty(totalVotos))) {
                    if(!(TextUtils.isEmpty(votosBrancos))){
                        Toast.makeText(this, "Escolha votos brancos ou total votos", Toast.LENGTH_SHORT).show()

                    }else if(!(TextUtils.isEmpty(votosNulos))){
                        Toast.makeText(this, "Escolha votos nulos ou total votos", Toast.LENGTH_SHORT).show()

                    }else{
                        zonaEscolhida = numZona
                        secaoEscolhida = numSecao
                        cadastraDadosUrna(numZona, numSecao, totalVotos, votosBrancos, votosNulos, presidenteEscolhido)

                    }
                }else{
                    if(!(TextUtils.isEmpty(votosBrancos)) && !(TextUtils.isEmpty(votosNulos))){
                        zonaEscolhida = numZona
                        secaoEscolhida = numSecao
                        cadastraDadosUrna(numZona, numSecao, totalVotos, votosBrancos, votosNulos, "vazio")

                    }else{
                        Toast.makeText(this, "Preencha brancos e nulos ou total votos", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.button_graficos_nav -> {
                    val parciaisGraficas = Intent(this, ParciaisGraficas::class.java)
                    println("MUDOU PARA TELA GRAFICOS!")
                    startActivity(parciaisGraficas)
                    true
                }
                else -> {
                    false

                }
            }
        }
    }
    private  fun cadastraDadosUrna(numZona: String, numSecao: String, totalVotos: String,
        votosBrancos: String, votosNulos: String, escolhaPres: String) {

        val stringRequest = StringRequest(Request.Method.GET,
            url+"/insert_cad_urna.php?numZona="+numZona+"&&numSecao="+numSecao+
                    "&&totalVotos="+totalVotos+"&&votosBrancos="+votosBrancos+
                    "&&votosNulos="+votosNulos+"&&nomeCandidato="+escolhaPres+
                    "&&emailUser="+emailLogado+"&&nomeCidade="+cidadeEscolhida+"&&regValido="+"0",
            { s ->
                try {
                    val obj = JSONArray(s)
                    if(escolhaPres != "vazio"){
                        for (i in 0..obj.length()-1) {
                            val objectUrna = obj.getJSONObject(i)
                            if (objectUrna.get("idDadosUrna").toString() != "null") {
                                println("idDadosUrna: " + objectUrna.get("idDadosUrna").toString())
                                Toast.makeText(
                                    this,
                                    "Presidente já registrado para essa seçao",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Timer().schedule(2000) {

                                }
                            } else {
                                Toast.makeText(this, "Cadastro realizado!", Toast.LENGTH_SHORT).show()
                                println("idDadosUrna: " + objectUrna.get("idDadosUrna").toString())
                                Timer().schedule(2000) {

                                }
                            }
                        }
                    }else{
                        for (i in 0..obj.length()-1) {
                            val objectUrna = obj.getJSONObject(i)
                            if((objectUrna.get("totalVotosBrancos").toString() != "null") && (objectUrna.get("totalVotosNulos").toString() != "null")){
                                println("totalVotosBrancos: "+ objectUrna.get("totalVotosBrancos").toString())
                                println("totalVotosNulos: "+ objectUrna.get("totalVotosNulos").toString())
                                Toast.makeText(this, "Votos brancos e nulos já registrado para essa seçao", Toast.LENGTH_SHORT).show()
                                Timer().schedule(2000){

                                }
                            }else{
                                Toast.makeText(this, "Votos brancos e nulos registrado!", Toast.LENGTH_SHORT).show()
                                println("totalVotosBrancos: "+ objectUrna.get("totalVotosBrancos").toString())
                                println("totalVotosNulos: "+ objectUrna.get("totalVotosNulos").toString())
                                Timer().schedule(2000){

                                }
                            }
                        }
                    }
                } catch (e: JSONException){
                    e.printStackTrace()

                }
            }, {
                    error: VolleyError? -> println("Erro ")

            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }
    private fun mostrarListaEstados(estados: MutableList<String>) {
        val stringRequest = StringRequest(com.android.volley.Request.Method.GET, url+"/select_estados.php",
            { s ->
                try {
                    val obj = JSONArray(s)
                    for (i in 0..obj.length()-1) {
                        val objectEstado = obj.getJSONObject(i)
                        val estado = Estado()
                        estado.setNome(objectEstado.get("nome").toString())
                        estado.setId(objectEstado.get("idEstado").toString())

                        estados.add(estado.nome.toString())

                    }
                    val spinnerEstados: Spinner = binding.inputSelectEstados
                    geradorSpinnerEstado(estados, spinnerEstados)


                } catch (e: JSONException){
                    e.printStackTrace()

                }
            }, {
                error: VolleyError? -> println("Erro ")

            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }
    private fun mostrarListaCidades(cidades: MutableList<String>) {
        val stringRequest = StringRequest(com.android.volley.Request.Method.POST,
            url+"/select_cidades.php?nomeEstado="+ estadoEscolhido,
            { s ->
                try {
                    val obj = JSONArray(s)
                    for (i in 0..obj.length()-1) {
                        val objectCidade = obj.getJSONObject(i)
                        val cidade = Cidade()

                        cidade.setNome(objectCidade.get("nome").toString())
                        cidade.setId(objectCidade.get("idMunicipio").toString())

                        cidades.add(cidade.nome.toString())

                    }
                    val spinnerCidades: Spinner = binding.inputSelectCidades
                    geradorSpinnerCidade(cidades, spinnerCidades)

                } catch (e: JSONException){
                    e.printStackTrace()

                }
            }, {
                    error: VolleyError? -> println("Erro ")

            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }

    private fun mostrarListaCandidatos(candidatos: MutableList<String>,  spinner: Spinner, categoria: Int
    ) {
        val stringRequest = StringRequest(com.android.volley.Request.Method.GET,
            url+"/select_candidato.php?categoria="+categoria,
            { s ->
                try {
                    val obj = JSONArray(s)
                    for (i in 0..obj.length()-1) {
                        val objectCandidato = obj.getJSONObject(i)
                        val candidato = Candidato()
                        candidato.setNome(objectCandidato.get("nome").toString())

                        candidatos.add(candidato.nome.toString())

                    }
                    geradorSpinnerCandidatos(candidatos, spinner, categoria)


                } catch (e: JSONException){
                    e.printStackTrace()

                }
            }, {
                    error: VolleyError? -> println("Erro ")

            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }
    private fun geradorSpinnerEstado(vetor: MutableList<String>, spinner: Spinner) {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.style_selects, vetor)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                estadoEscolhido = removeAccent(vetor[p2]).toString()
                println("Estado escolhido: "+ estadoEscolhido)

                val cidades = mutableListOf<String>()
                mostrarListaCidades(cidades)

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    private fun geradorSpinnerCidade(vetor: MutableList<String>, spinner: Spinner) {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.style_selects, vetor)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                cidadeEscolhida = removeAccent(vetor[p2]).toString()
                println("Cidade escolhida: "+ cidadeEscolhida)

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun geradorSpinnerCandidatos(vetor: MutableList<String>, spinner: Spinner, categoria: Int) {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.style_selects, vetor)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if(categoria == 1){
                    println("Presidente escolhido: "+ vetor[p2])
                    presidenteEscolhido = vetor[p2]

                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    fun removeAccent(str: String?): String? {
        var strNoAccent = Normalizer.normalize(str, Normalizer.Form.NFD)
        strNoAccent = strNoAccent.replace("[^\\p{ASCII}]".toRegex(), "")
        return strNoAccent

    }
    fun getUser(): String? {
        val userId = MainActivity().getEmailLogadoId()
        return userId

    }
    fun getCandidato(): String? {
        return presidenteEscolhido

    }
    fun getCidade(): String? {
        return cidadeEscolhida

    }
    fun getNumZona(): String? {
        return zonaEscolhida

    }
    fun getNumSecao(): String? {
        return secaoEscolhida

    }
}