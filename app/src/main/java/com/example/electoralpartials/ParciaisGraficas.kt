package com.example.electoralpartials

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.electoralpartials.databinding.ActivityParciaisGraficasBinding
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.json.JSONArray
import org.json.JSONException
import java.lang.Float.parseFloat
import java.lang.Integer.parseInt
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

private lateinit var binding: ActivityParciaisGraficasBinding
class ParciaisGraficas : AppCompatActivity() {
    /*
    lateinit var barList: ArrayList<BarEntry>
    lateinit var barDataSet: BarDataSet
    lateinit var barData: BarData
    */
    lateinit var pieList: ArrayList<PieEntry>
    lateinit var pieDataSet: PieDataSet
    lateinit var pieData: PieData

    lateinit var pieList2: ArrayList<PieEntry>
    lateinit var pieDataSet2: PieDataSet
    lateinit var pieData2: PieData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParciaisGraficasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonRefreshGraficos.setOnClickListener{
            consultaParciais()

        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.button_add_nav -> {
                    val cadUrna = Intent(this, CadUrna::class.java)
                    println("MUDOU PARA TELA CAD URNA!")
                    startActivity(cadUrna)
                    true
                }
                else -> {
                    false

                }
            }
        }
        //graficoPiePresidente()
        consultaParciais()
        //consultaParciaisTse()

    }

    private fun consultaParciais() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            url + "/select_parciais_pres.php?op=" + "1",
            { s ->
                try {
                    val obj = JSONArray(s)

                    val pieChart = binding.pieChart
                    pieList = ArrayList()

                    for (i in 0..obj.length() - 1) {
                        val objectParciais = obj.getJSONObject(i)
                        if (objectParciais.get("total").toString() != "0") {
                            println("total: " + objectParciais.get("total").toString())
                            println("nome: " + objectParciais.get("nome").toString())

                            pieList.add(PieEntry(objectParciais.get("total").toString().toFloat(), objectParciais.get("nome").toString()))

                        } else {
                            Timer().schedule(2000) {
                                println("total: " + objectParciais.get("total").toString())
                                println("nome: " + objectParciais.get("nome").toString())
                                pieList.add(PieEntry(0f, "vazio"))

                            }
                        }
                    }
                    pieDataSet = PieDataSet(pieList, " -Mais Votado")
                    pieData = PieData(pieDataSet)
                    pieChart.data = pieData
                    pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
                    pieDataSet.valueTextColor = Color.BLACK
                    pieDataSet.valueTextSize = 16f
                    //pieChart.description.text = "Parciaiss"
                    pieChart.centerText = "Parciais"
                    pieChart.animateY(2000)
                    pieChart.setEntryLabelColor(Color.BLACK)
                    pieChart.setEntryLabelTextSize(11f)
                    pieChart.legend.textColor = Color.WHITE

                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            }, { error: VolleyError? ->
                println("Erro ")

            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }
    private fun consultaParciaisTse() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            url + "/select_parciais_pres_tse.php?op=" + "1",
            { s ->
                try {
                    val obj = JSONArray(s)

                    val pieChart2 = binding.pieChart2
                    pieList2 = ArrayList()

                    for (i in 0..obj.length() - 1) {
                        val objectParciaisTse = obj.getJSONObject(i)
                        if (objectParciaisTse.get("total").toString() != "0") {
                            println("total: " + objectParciaisTse.get("total").toString())
                            println("nome: " + objectParciaisTse.get("nome").toString())

                            pieList2.add(PieEntry(objectParciaisTse.get("total").toString().toFloat(), objectParciaisTse.get("nome").toString()))

                        } else {
                            Timer().schedule(2000) {
                                println("total: " + objectParciaisTse.get("total").toString())
                                println("nome: " + objectParciaisTse.get("nome").toString())
                                pieList2.add(PieEntry(0f, "vazio"))

                            }
                        }
                    }
                    pieDataSet2 = PieDataSet(pieList2, " -Mais Votado")
                    pieData2 = PieData(pieDataSet2)
                    pieChart2.data = pieData2
                    pieDataSet2.setColors(ColorTemplate.MATERIAL_COLORS, 255)
                    pieDataSet2.valueTextColor = Color.BLACK
                    pieDataSet2.valueTextSize = 16f
                    //pieChart.description.text = "Parciaiss"
                    pieChart2.centerText = "Parciais"
                    pieChart2.animateY(2000)
                    pieChart2.setEntryLabelColor(Color.BLACK)
                    pieChart2.setEntryLabelTextSize(11f)
                    pieChart2.legend.textColor = Color.WHITE

                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            }, { error: VolleyError? ->
                println("Erro ")

            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }
    /*
    private fun graficoBarPresidente() {
        val barChart = binding.barChart

        barList = ArrayList()
        barList.add(BarEntry(1f, 500f))
        barList.add(BarEntry(2f, 100f))
        barList.add(BarEntry(3f, 300f))
        barList.add(BarEntry(4f, 500f))

        barDataSet = BarDataSet(barList, "Parciais")
        barData = BarData(barDataSet)
        barChart.data = barData
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS, 170)
        barDataSet.valueTextColor = Color.WHITE
        barDataSet.valueTextSize = 16f
        barChart.getXAxis().setTextColor(Color.WHITE)
        barChart.getAxis(YAxis.AxisDependency.LEFT).setTextColor(Color.WHITE)
        barChart.getAxis(YAxis.AxisDependency.RIGHT).setTextColor(Color.WHITE)
        barChart.legend.textColor = Color.WHITE

    }

    private fun graficoPiePresidente() {
        val pieChart = binding.pieChart

        pieList = ArrayList()
        /*
        pieList.add(PieEntry(100f, "Bolsonaro"))
        pieList.add(PieEntry(80f, "Lula"))
        pieList.add(PieEntry(110f, "Ciro"))
        */
        pieDataSet = PieDataSet(pieList, " -Parciais")
        pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 18f
        //pieChart.description.text = "Parciaiss"
        pieChart.centerText = "Parciais"
        pieChart.animateY(2000)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setEntryLabelTextSize(16f)
        pieChart.legend.textColor = Color.WHITE

    }
    */
}