package com.example.electoralpartials

import android.content.Intent
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
import com.example.electoralpartials.databinding.ActivityAdicionarAdminBinding
import com.example.electoralpartials.databinding.ActivityCadAdminBinding
import com.example.electoralpartials.databinding.ActivityCadUserBinding
import com.example.electoralpartials.model.Estado
import com.example.electoralpartials.model.User
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Integer.parseInt
import java.text.Normalizer
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

private  lateinit var  binding: ActivityAdicionarAdminBinding
var userEscolhido: String = ""

class AdicionarAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val users = mutableListOf<String>()
        mostrarListaUsers(users)

        binding.buttonCadAdminSalvar.setOnClickListener{
            addAdministrador(userEscolhido, "2")

        }
        binding.buttonRemoverAdminSalvar.setOnClickListener{
           addAdministrador(userEscolhido, "0")

        }
        binding.bottomNavigationAdmin.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.button_like_cand_nav -> {
                    val likeCand = Intent(this, AvaliarAdmin::class.java)
                    println("MUDOU PARA TELA AVALIAÇÃO!")
                    startActivity(likeCand)
                    true
                }
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

    private fun mostrarListaUsers(users: MutableList<String>) {
        val stringRequest =
            StringRequest(com.android.volley.Request.Method.GET,
                url + "/select_users.php?email="+ emailLogado,
                { s ->
                    try {
                        val obj = JSONArray(s)
                        for (i in 0..obj.length() - 1) {
                            val objectUsers = obj.getJSONObject(i)
                            val user = User()
                            user.setEmail(objectUsers.get("email").toString())

                            users.add(user.email.toString())

                        }
                        val spinnerUsers: Spinner = binding.inputSelectUsers
                        geradorSpinnerUsers(users, spinnerUsers)


                    } catch (e: JSONException) {
                        e.printStackTrace()

                    }
                }, { error: VolleyError? ->
                    println("Erro ")

                })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }

    private fun geradorSpinnerUsers(vetor: MutableList<String>, spinner: Spinner) {
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.style_selects, vetor)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                userEscolhido = removeAccent(vetor[p2]).toString()
                println("User escolhido: " + userEscolhido)


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    private fun addAdministrador(userEscolhido: String, op: String) {
        val stringRequest = StringRequest(com.android.volley.Request.Method.GET,
            url+"/update_user_admin.php?email="+ userEscolhido+"&&op="+op,
            { s ->
                try {
                    val obj = JSONArray(s)
                    for (i in 0..obj.length()-1) {
                        val objectUser = obj.getJSONObject(i)
                        if(objectUser.get("idUsuario").toString() != "0"){
                            println("IdUsuario: "+ objectUser.get("idUsuario").toString())
                            Toast.makeText(this, "Esse usuario já é admin", Toast.LENGTH_SHORT).show()
                            Timer().schedule(2000){


                            }
                        }else{
                            Toast.makeText(this, "Alteração realizada!", Toast.LENGTH_SHORT).show()
                            Timer().schedule(2000){

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
    fun removeAccent(str: String?): String? {
        var strNoAccent = Normalizer.normalize(str, Normalizer.Form.NFD)
        strNoAccent = strNoAccent.replace("[^\\p{ASCII}]".toRegex(), "")
        return strNoAccent

    }
}