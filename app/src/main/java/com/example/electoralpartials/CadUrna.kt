package com.example.electoralpartials

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

class CadUrna : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadUrnaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val estados = mutableListOf("Selecione um estado")
        mostrarListaEstados(estados)


        val presidentes = mutableListOf("Selecione um presidente", "Bolsonaro", "Lula")
        val spinnerPresidentes: Spinner = binding.inputSelectPresidente
        geradorSpinnerCandidatos(presidentes, spinnerPresidentes)

        //val governadores = arrayOf("Selecione um governador", "Carlos", "Pedro")
        //val spinnerGovernadores: Spinner = binding.inputSelectGovernador
        //geradorListas(governadores, spinnerGovernadores)

        //val senadores = arrayOf("Selecione um Senador", "João", "Farias")
        //val spinnerSenadores: Spinner = binding.inputSelectSenador
        //geradorListas(senadores, spinnerSenadores)



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
                        estado.setId(objectEstado.get("id").toString())

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
                        //val cidade = Estado(objectEstado.getString("nome"))
                        val cidade = Cidade()

                        cidade.setNome(objectCidade.get("nome").toString())
                        cidade.setId(objectCidade.get("idEstado").toString())

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
    private fun geradorSpinnerCandidatos(vetor: MutableList<String>, spinner: Spinner) {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.style_selects, vetor)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                println("Candidato escolhido: "+ vetor[p2])
                presidenteEscolhido = vetor[p2]

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