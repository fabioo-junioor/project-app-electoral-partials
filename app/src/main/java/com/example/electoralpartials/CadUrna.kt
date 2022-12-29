package com.example.electoralpartials

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
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

private lateinit var binding: ActivityCadUrnaBinding
val url = "http://10.0.2.2/electoralPartialsBackEnd"

var estadoEscolhido: String = ""
var cidadeEscolhida: String = ""
var presidenteEscolhido: String = ""
var governadorEscolhido: String = ""
var senadorEscolhido: String = ""
var deputadoFedEscolhido: String = ""
var deputadoEstEscolhido: String = ""

class CadUrna : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadUrnaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val estados = mutableListOf("Selecione um estado")
        mostrarListaEstados(estados)


        val presidentes = mutableListOf("Selecione um presidente")
        val spinnerPresidentes: Spinner = binding.inputSelectPresidente
        mostrarListaCandidatos(presidentes, spinnerPresidentes, 1)

        val governadores = mutableListOf("Selecione um governador")
        val spinnerGovernadores: Spinner = binding.inputSelectGovernador
        mostrarListaCandidatos(governadores, spinnerGovernadores, 2)

        val senadores = mutableListOf("Selecione um Senador")
        val spinnerSenadores: Spinner = binding.inputSelectSenador
        mostrarListaCandidatos(senadores, spinnerSenadores, 3)

        val depFederal = mutableListOf("Selecione um Deputado Federal")
        val spinnerDepFederal: Spinner = binding.inputSelectDepFed
        mostrarListaCandidatos(depFederal, spinnerDepFederal, 4)

        val depEstadual = mutableListOf("Selecione um Deputado Estadual")
        val spinnerDepEstadual: Spinner = binding.inputSelectDepEst
        mostrarListaCandidatos(depEstadual, spinnerDepEstadual, 5)


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
                println("Estado escolhido: "+ vetor[p2])
                estadoEscolhido = removeAccent(vetor[p2]).toString()

                val cidades = mutableListOf("Selecione uma cidade")
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
                println("Cidade escolhida: "+ vetor[p2])
                cidadeEscolhida = vetor[p2]

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

                }else if(categoria == 2){
                    println("Governador escolhido: "+ vetor[p2])
                    governadorEscolhido = vetor[p2]

                }else if(categoria == 3){
                    println("Senador escolhido: "+ vetor[p2])
                    senadorEscolhido = vetor[p2]

                }else if(categoria == 4){
                    println("Deputado Federal escolhido: "+ vetor[p2])
                    deputadoFedEscolhido = vetor[p2]

                }else if(categoria == 5){
                    println("Deputado Estadual escolhido: "+ vetor[p2])
                    deputadoEstEscolhido = vetor[p2]

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
}