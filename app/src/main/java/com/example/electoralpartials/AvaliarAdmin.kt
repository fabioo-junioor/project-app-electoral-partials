package com.example.electoralpartials

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.electoralpartials.databinding.ActivityAvaliarAdminBinding
import com.example.electoralpartials.databinding.ActivityCadAdminBinding
import com.example.electoralpartials.databinding.ActivityCadUserBinding
import com.example.electoralpartials.model.Cidade
import com.example.electoralpartials.model.Estado
import com.example.electoralpartials.model.Secao
import com.example.electoralpartials.model.Zona
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Integer.parseInt
import java.text.Normalizer
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

private  lateinit var  binding: ActivityAvaliarAdminBinding

var estadoEscolhidoAval: String = ""
var cidadeEscolhidaAval: String = ""
var zonaEscolhidoAval: String = ""
var secaoEscolhidaAval: String = ""

class AvaliarAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvaliarAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val estados = mutableListOf<String>()
        mostrarListaEstados(estados)

        binding.bottomNavigationAdmin.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.button_add_cand_nav -> {
                    val addCand = Intent(this, CadAdmin::class.java)
                    println("MUDOU PARA TELA CADASTRO CANDIDATO!")
                    startActivity(addCand)
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
                    val spinnerEstados: Spinner = binding.inputSelectEstadosAval
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
    private fun geradorSpinnerEstado(vetor: MutableList<String>, spinner: Spinner) {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.style_selects, vetor)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                estadoEscolhidoAval = removeAccent(vetor[p2]).toString()
                println("Estado escolhido: "+ estadoEscolhidoAval)

                val cidades = mutableListOf<String>()
                mostrarListaCidades(cidades)

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    private fun mostrarListaCidades(cidades: MutableList<String>) {
        val stringRequest = StringRequest(com.android.volley.Request.Method.POST,
            url+"/select_cidades.php?nomeEstado="+ estadoEscolhidoAval,
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
                    val spinnerCidades: Spinner = binding.inputSelectCidadesAval
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
    private fun geradorSpinnerCidade(vetor: MutableList<String>, spinner: Spinner) {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.style_selects, vetor)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                cidadeEscolhidaAval = removeAccent(vetor[p2]).toString()
                println("Cidade escolhida: "+ cidadeEscolhidaAval)

                val zonas = mutableListOf<String>()
                mostrarListaZonas(zonas)


            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    private fun mostrarListaZonas(zonas: MutableList<String>) {
        val stringRequest = StringRequest(com.android.volley.Request.Method.POST,
            url+"/select_zonas.php?nomeCidade="+ cidadeEscolhidaAval,
            { s ->
                try {
                    val obj = JSONArray(s)
                    for (i in 0..obj.length()-1) {
                        val objectCidade = obj.getJSONObject(i)
                        val zona = Zona()

                        zona.setNumero(objectCidade.get("numZona").toString())

                        zonas.add(zona.numero.toString())

                    }
                    val spinnerZonas: Spinner = binding.inputSelectZonaAval
                    geradorSpinnerZonas(zonas, spinnerZonas)

                } catch (e: JSONException){
                    e.printStackTrace()

                }
            }, {
                    error: VolleyError? -> println("Erro ")

            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }
    private fun geradorSpinnerZonas(vetor: MutableList<String>, spinner: Spinner) {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.style_selects, vetor)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                zonaEscolhidoAval = removeAccent(vetor[p2]).toString()
                println("Zona escolhida: "+ zonaEscolhidoAval)

                val secoes = mutableListOf<String>()
                mostrarListaSecao(secoes)


            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    private fun mostrarListaSecao(secoes: MutableList<String>) {
        val stringRequest = StringRequest(com.android.volley.Request.Method.POST,
            url+"/select_secao.php?numZona="+ zonaEscolhidoAval,
            { s ->
                try {
                    val obj = JSONArray(s)
                    for (i in 0..obj.length()-1) {
                        val objectCidade = obj.getJSONObject(i)
                        val secao = Secao()

                        secao.setNumero(objectCidade.get("numSecao").toString())

                        secoes.add(secao.numero.toString())

                    }
                    val spinnerSecoes: Spinner = binding.inputSelectSecaoAval
                    geradorSpinnerSecoes(secoes, spinnerSecoes)

                } catch (e: JSONException){
                    e.printStackTrace()

                }
            }, {
                    error: VolleyError? -> println("Erro ")

            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }
    private fun geradorSpinnerSecoes(vetor: MutableList<String>, spinner: Spinner) {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.style_selects, vetor)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                secaoEscolhidaAval = removeAccent(vetor[p2]).toString()
                println("Secao escolhida: "+ secaoEscolhidaAval)

                dadosEscolhidos()

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    private fun dadosEscolhidos() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            url + "/select_dados_aval.php?numZona="+ zonaEscolhidoAval+
                        "&&numSecao="+ secaoEscolhidaAval+"&&nomeCidade="+ cidadeEscolhidaAval,
            { s ->
                try {
                    val obj = JSONArray(s)
                    val dados = mutableListOf<String>()
                    for (i in 0..obj.length() - 1) {
                        val objectUser = obj.getJSONObject(i)
                        if (objectUser.get("user").toString() != "null") {
                            if(objectUser.get("votos").toString() != "0"){
                                println("user-: " + objectUser.get("user").toString())
                                println("secao-: " + objectUser.get("secao").toString())
                                println("nome-: " + objectUser.get("nomeC").toString())

                                dados.add("User: "+objectUser.get("user").toString())
                                dados.add("Secao: "+objectUser.get("secao").toString())
                                dados.add("\nNome: "+objectUser.get("nomeC").toString())
                                //binding.textResponseDadosAval.text = dados.toString()

                            }
                            if(objectUser.get("brancos").toString() != "0"){
                                println("votos brancos-: " + objectUser.get("brancos").toString())
                                println("votos nulos-: " + objectUser.get("nulos").toString())
                                dados.add("brancos: "+objectUser.get("brancos").toString())
                                dados.add("nulos: "+objectUser.get("nulos").toString())
                                //binding.textResponseDadosAval.text = dados.toString()

                            }
                            dados.add("\n")
                            binding.textResponseDadosAval.text = dados.toString()
                        } else {
                            //println("votos brancos-: " + objectUser.get("votos brancos").toString())
                            //println("votos nulos-: " + objectUser.get("votos nulos").toString())

                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            }, { error: VolleyError? ->
                println("Erro ")

            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }
    fun removeAccent(str: String?): String? {
        var strNoAccent = Normalizer.normalize(str, Normalizer.Form.NFD)
        strNoAccent = strNoAccent.replace("[^\\p{ASCII}]".toRegex(), "")
        return strNoAccent

    }
}