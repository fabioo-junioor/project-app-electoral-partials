package com.example.electoralpartials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType.*
import android.text.TextUtils
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.electoralpartials.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import kotlin.concurrent.schedule

private  lateinit var  binding: ActivityMainBinding
var emailLogado: String = ""

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //navegarTelas(2)
        binding.btnLogin.setOnClickListener{
            loginUser()

        }
        binding.checkBoxMostrarSenhaLogin.setOnClickListener{
            mostrarSenha()

        }
        binding.linkCadastrarSe.setOnClickListener {
            navegarTelas(1)

        }
    }
    private  fun mostrarSenha (){
        val textSenha = binding.loginSenha
        val checkedSenha = binding.checkBoxMostrarSenhaLogin.isChecked
        if(checkedSenha){
            println("Marcou!")
            textSenha.inputType = 1

        }else{
            textSenha.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
            println("Desmarcou!")


        }
    }
    private fun loginUser(){
        val email_user = binding.loginEmail.text.toString()
        val senha_user = binding.loginSenha.text.toString()

        if(TextUtils.isEmpty(email_user)){
            Toast.makeText(this, "Preencha o campo Email!", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(senha_user)){
            Toast.makeText(this, "Preencha o campo Senha!", Toast.LENGTH_SHORT).show()

        }else{
           verificaUsuario(email_user, senha_user)

        }
    }
    private fun verificaUsuario(email_user: String, senha_user: String) {
        val stringRequest = StringRequest(Request.Method.GET,
            url+"/login_user.php?email="+email_user+"&&senha="+senha_user,
            { s ->
                try {
                    val obj = JSONArray(s)
                    for (i in 0..obj.length()-1) {
                        val objectUser = obj.getJSONObject(i)
                        if(Integer.parseInt(objectUser.get("idUsuario").toString()) != 0){
                            println("IdUsuario: "+ objectUser.get("idUsuario").toString())
                            Toast.makeText(this, "Login efetuado", Toast.LENGTH_SHORT).show()
                            if(Integer.parseInt(objectUser.get("idUsuario").toString()) == 1){
                                emailLogado = email_user
                                Timer().schedule(2000){
                                    navegarTelas(3)

                                }
                            }else{
                                emailLogado = email_user
                                Timer().schedule(2000){
                                    navegarTelas(2)

                                }
                            }
                        }else{
                            Toast.makeText(this, "Usuario não cadastrado", Toast.LENGTH_SHORT).show()
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
    private fun navegarTelas(codigo: Number){
        if(codigo == 1){
            startActivity(Intent(this, CadUser::class.java))

        }else if(codigo == 2){
            startActivity(Intent(this, CadUrna::class.java))

        }else if(codigo == 3){
            startActivity(Intent(this, CadAdmin::class.java))

        }
    }
}