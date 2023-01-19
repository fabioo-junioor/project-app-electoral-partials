package com.example.electoralpartials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.electoralpartials.databinding.ActivityCadAdminBinding
import com.example.electoralpartials.databinding.ActivityCadUserBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Integer.parseInt
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

private  lateinit var  binding: ActivityCadAdminBinding

class CadAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonCadCandSalvar.setOnClickListener {
            cadAdmin()

        }
        binding.bottomNavigationAdmin.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.button_like_cand_nav -> {
                    val likeCand = Intent(this, AvaliarAdmin::class.java)
                    println("MUDOU PARA TELA AVALIAÇÃO!")
                    startActivity(likeCand)
                    true
                }
                R.id.button_add_cand_admin -> {
                    val cadAdmin = Intent(this, AdicionarAdmin::class.java)
                    println("MUDOU PARA TELA ADICIONAR ADMIN!")
                    startActivity(cadAdmin)
                    true
                }
                else -> {
                    false

                }
            }
        }
    }
    private fun cadAdmin(){
        val cand_nome = binding.inputCadCandNome.text.toString()
        val cand_sigla = binding.inputCadCandSigla.text.toString()
        val cand_numero = binding.inputCadCandNumero.text.toString()

        if(TextUtils.isEmpty(cand_nome)){
            Toast.makeText(this, "Preencha o campo Nome!", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(cand_sigla)){
            Toast.makeText(this, "Preencha o campo Sigla!", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(cand_numero)) {
            Toast.makeText(this, "Preencha o campo Numero!", Toast.LENGTH_SHORT).show()

        }else{
            addNovoCandidato(cand_nome, cand_sigla, cand_numero)

        }

    }
    private fun addNovoCandidato(cand_nome: String, cand_sigla: String, cand_numero: String) {
        val stringRequest = StringRequest(com.android.volley.Request.Method.GET,
            url+"/insert_cad_candidatos.php?nomeCand="+cand_nome+"&&siglaCand="+cand_sigla+"&&numCand="+cand_numero,
            { s ->
                try {
                    val obj = JSONArray(s)
                    for (i in 0..obj.length()-1) {
                        val objectCand = obj.getJSONObject(i)
                        if(objectCand.get("idCandidato").toString() != "null"){
                            println("IdCandidato: "+ objectCand.get("idCandidato").toString())
                            Toast.makeText(this, "Esse numero já existe", Toast.LENGTH_SHORT).show()
                            Timer().schedule(3000){

                            }
                        }else{
                            Toast.makeText(this, "Cadastro realizado!", Toast.LENGTH_SHORT).show()
                            Timer().schedule(2000){
                                println("Nome: "+ cand_nome)
                                println("Sigla: "+ cand_sigla)
                                println("Numero: "+ cand_numero)

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
}