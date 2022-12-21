package com.example.electoralpartials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.electoralpartials.databinding.ActivityCadUserBinding
import com.example.electoralpartials.databinding.ActivityMainBinding
import java.util.*
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
            if(cad_email == "fa"){
                Toast.makeText(this, "Email já cadastrado no sistema!", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                Timer().schedule(2000){
                    println("Login sucesso!!")
                    println("Nome: "+ cad_nome)
                    println("Email: "+ cad_email)
                    println("Senha: "+ cad_senha)

                    navegarTelaMain()

                }
            }
        }
    }
    private fun navegarTelaMain(){
        startActivity(Intent(this, MainActivity::class.java))

    }
}