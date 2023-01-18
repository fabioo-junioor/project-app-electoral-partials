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
import com.example.electoralpartials.databinding.ActivityAvaliarAdminBinding
import com.example.electoralpartials.databinding.ActivityCadAdminBinding
import com.example.electoralpartials.databinding.ActivityCadUserBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Integer.parseInt
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

private  lateinit var  binding: ActivityAvaliarAdminBinding

class AvaliarAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvaliarAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
}