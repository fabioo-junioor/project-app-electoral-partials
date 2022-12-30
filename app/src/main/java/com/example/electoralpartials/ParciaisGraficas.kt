package com.example.electoralpartials

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.electoralpartials.databinding.ActivityParciaisGraficasBinding
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

private lateinit var binding: ActivityParciaisGraficasBinding
class ParciaisGraficas : AppCompatActivity() {
    lateinit var barList: ArrayList<BarEntry>
    lateinit var barDataSet: BarDataSet
    lateinit var barData: BarData

    lateinit var pieList: ArrayList<PieEntry>
    lateinit var pieDataSet: PieDataSet
    lateinit var pieData: PieData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParciaisGraficasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navegacaoMenu()
        graficoBarPresidente()
        graficoPiePresidente()

    }
    private fun graficoBarPresidente(){
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
    private fun graficoPiePresidente(){
        val pieChart = binding.pieChart

        pieList = ArrayList()
        pieList.add(PieEntry(100f, "Bolsonaro"))
        pieList.add(PieEntry(80f, "Lula"))
        pieList.add(PieEntry(110f, "Ciro"))

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
    private fun navegacaoMenu(){
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
    }
}