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
import com.example.electoralpartials.databinding.ActivityCadUserBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Integer.parseInt
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

private  lateinit var  binding: ActivityCadUserBinding

class CadUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonCadVoltar.setOnClickListener{
            val btn_voltar = Intent(this, MainActivity::class.java)
            startActivity(btn_voltar)

        }
        binding.buttonCadSalvar.setOnClickListener {
            cadUser()

        }
    }
    private fun cadUser(){
        val cad_nome = binding.inputCadNome.text.toString()
        val cad_email = binding.inputCadEmail.text.toString()
        val cad_senha = binding.inputCadSenha.text.toString()

        if(TextUtils.isEmpty(cad_nome)){
            Toast.makeText(this, "Preencha o campo Nome!", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(cad_email)){
            Toast.makeText(this, "Preencha o campo Email!", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(cad_senha)) {
            Toast.makeText(this, "Preencha o campo Senha!", Toast.LENGTH_SHORT).show()

        }else{
            addNovoUsuario(cad_nome, cad_email, cad_senha)

        }
    }
    /*
    private fun addNovoUsuario(cad_nome: String, cad_email: String, cad_senha: String) {
        val stringRequest = object: StringRequest(Request.Method.POST, url+"/insert_user.php",
        Response.Listener<String> { response ->
            try {
                val obj = JSONObject(response)
                println("error-> "+obj.get("idUsuario"))

            }catch (e: JSONException){
                e.printStackTrace()

            }
        }, object: Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    TODO("Not yet implemented")
                }
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params.put("nome", cad_nome)
                params.put("email", cad_email)
                params.put("senha", cad_senha)
                return params

            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)


    }
    */
    private fun addNovoUsuario(cad_nome: String, cad_email: String, cad_senha: String) {
        val stringRequest = StringRequest(com.android.volley.Request.Method.GET,
            url+"/insert_user.php?nome="+cad_nome+"&&email="+cad_email+"&&senha="+cad_senha,
            { s ->
                try {
                    val obj = JSONArray(s)
                    for (i in 0..obj.length()-1) {
                        val objectUser = obj.getJSONObject(i)
                        if(parseInt(objectUser.get("idUsuario").toString()) != 0){
                            println("IdUsuario: "+ objectUser.get("idUsuario").toString())
                            Toast.makeText(this, "Email já cadastrado por outro usuario", Toast.LENGTH_SHORT).show()
                            Timer().schedule(3000){


                            }
                        }else{
                            Toast.makeText(this, "Cadastro realizado!", Toast.LENGTH_SHORT).show()
                            Timer().schedule(2000){
                                println("Nome: "+ cad_nome)
                                println("Email: "+ cad_email)
                                println("Senha: "+ cad_senha)
                                navegarTelaMain()

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
    private fun navegarTelaMain(){
        startActivity(Intent(this, MainActivity::class.java))

    }
}