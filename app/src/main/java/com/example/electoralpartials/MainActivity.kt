package com.example.electoralpartials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType.*
import android.text.TextUtils
import android.widget.Toast
import com.example.electoralpartials.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.schedule

private  lateinit var  binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //binding.loginSenha.inputType = TYPE_TEXT_VARIATION_PASSWORD or TYPE_CLASS_TEXT
        binding.btnLogin.setOnClickListener{
            loginUser()

        }
        binding.checkBoxMostrarSenhaLogin.setOnClickListener{
            mostrarSenha()

        }
        binding.linkCadastrarSe.setOnClickListener {
            navegarTelaCad()

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
            Toast.makeText(this, "Login Efetuado!", Toast.LENGTH_SHORT).show()
            Timer().schedule(2000){
                println("Login sucesso!!")
                println("Email: "+ email_user)
                println("Senha: "+ senha_user)

            }
        }
    }
    private fun navegarTelaCad(){
        val cad_user = Intent(this, CadUser::class.java)
        startActivity(cad_user)

    }
}