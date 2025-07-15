package be.buithg.etghaifgte.presentation.ui.fragments.main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.buithg.etghaifgte.R
import be.buithg.etghaifgte.databinding.FragmentStatsBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class StatsFragment : Fragment(R.layout.fragment_stats) {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAccuracyChart()     // PieChart
        setupLineChart()         // LineChart
    }

    private fun setupAccuracyChart() {
        val chart = binding.pieChart
        chart.apply {
            description.isEnabled = false
            isRotationEnabled = false
            legend.isEnabled = false
            setDrawEntryLabels(false)
            setUsePercentValues(false)
            setHoleColor(Color.TRANSPARENT)
            holeRadius = 90f
            transparentCircleRadius = 0f
            setDrawRoundedSlices(true)
        }
        val entries = listOf(PieEntry(30f), PieEntry(40f), PieEntry(30f))
        val ds = PieDataSet(entries, "").apply {
            colors = listOf(
                Color.parseColor("#FF4E4E"),
                Color.parseColor("#FFCE01"),
                Color.parseColor("#4EFF4E")
            )
            sliceSpace = 2f
            setDrawValues(false)
        }
        chart.data = PieData(ds)
        chart.invalidate()
    }

    private fun setupLineChart() {
        val chart = binding.lineChart

        // 1. Базовые
        chart.apply {
            setBackgroundColor(Color.TRANSPARENT)
            description.isEnabled = false
            legend.isEnabled = true
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)
            axisRight.isEnabled = false
        }

        // 2. Ось X
        val months = listOf("JAN","FEB","MAR","APR","MAY","SEP")
        chart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            textColor = Color.parseColor("#AAAAAA")
            textSize = 10f
            setDrawAxisLine(true)            // рисуем линию оси
            setDrawGridLines(true)
            enableGridDashedLine(10f, 10f, 0f)
            valueFormatter = IndexAxisValueFormatter(months)
            granularity = 1f
            yOffset = 8f
        }

        chart.setExtraOffsets(
            /* left  */ 0f,
            /* top   */ 0f,
            /* right */ 0f,
            /* bottom*/ 16f
        )

        // 3. Ось Y
        chart.axisLeft.apply {
            setDrawGridLines(true)
            enableGridDashedLine(10f,10f,0f)
            axisMinimum = 0f
            axisMaximum = 100f
            granularity = 20f
            textColor = Color.parseColor("#666666")
        }


        // 4. Данные
        fun makeSet(values: List<Float>, color: Int): LineDataSet {
            return LineDataSet(values.mapIndexed { i, v -> Entry(i.toFloat(), v) }, "").apply {
                this.color = color
                lineWidth = 2.5f
                setDrawCircles(true)
                circleRadius = 4f
                setCircleColor(color)
                circleHoleRadius = 2f
                circleHoleColor = Color.BLACK
                setDrawValues(false)
                mode = LineDataSet.Mode.CUBIC_BEZIER
                cubicIntensity = 0.2f
            }
        }

        val data2021 = listOf(25f,58f,57f,70f,12f,95f)
        val data2022 = listOf(50f,35f,80f,55f,40f,45f)
        val data2023 = listOf(40f,90f,85f,83f,65f,92f)
        val data2024 = listOf(55f,10f,35f,58f,90f,15f)

        val sets = listOf(
            makeSet(data2021, Color.parseColor("#007AFF")),
            makeSet(data2022, Color.parseColor("#4CD964")),
            makeSet(data2023, Color.parseColor("#FF9500")),
            makeSet(data2024, Color.parseColor("#32D3F2"))
        )

        chart.data = LineData(sets)
        chart.legend.apply {
            // рисуем снизу под графиком
            verticalAlignment   = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            orientation         = Legend.LegendOrientation.HORIZONTAL

            // форма и цвета
            form      = Legend.LegendForm.CIRCLE
            formSize  = 8f
            textColor = Color.parseColor("#CCCCCC")

            // отступы между точкой/текстом
            xEntrySpace = 16f
            yEntrySpace = 4f

            // легенда вне зоны графика
            setDrawInside(false)
            isEnabled = true

            // свои записи с цветами
            setCustom(listOf(
                LegendEntry("2021", Legend.LegendForm.CIRCLE, 8f, 2f, null, Color.parseColor("#007AFF")),
                LegendEntry("2022", Legend.LegendForm.CIRCLE, 8f, 2f, null, Color.parseColor("#4CD964")),
                LegendEntry("2023", Legend.LegendForm.CIRCLE, 8f, 2f, null, Color.parseColor("#FF9500")),
                LegendEntry("2024", Legend.LegendForm.CIRCLE, 8f, 2f, null, Color.parseColor("#32D3F2"))
            ))
        }


        chart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
