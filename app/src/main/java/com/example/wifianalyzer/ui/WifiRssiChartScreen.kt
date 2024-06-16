package com.example.wifianalyzer.ui

import android.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.example.wifianalyzer.viewmodel.WifiViewModel
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiRssiChartScreen(bssid: String, wifiViewModel: WifiViewModel) {
    val networkItem = wifiViewModel.getNetworkItemByBssid(bssid).observeAsState()

    networkItem.value?.let { item ->
        val rssiHistory = item.rssiHistory
        Scaffold(
            content = { innerPadding ->
                AndroidView(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                    factory = { context ->
                        LineChart(context).apply {
                            layoutParams = android.view.ViewGroup.LayoutParams(
                                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                                android.view.ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            xAxis.position = XAxis.XAxisPosition.BOTTOM
                            axisRight.isEnabled = false
                            description.isEnabled = false
                            setTouchEnabled(true)
                            setPinchZoom(true)
                            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                                override fun onValueSelected(e: Entry?, h: Highlight?) {
                                    e?.let {
                                        Toast.makeText(context, "Moc: ${it.y} dBm", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onNothingSelected() {
                                    // Do nothing
                                }
                            })
                        }
                    },
                    update = { chart ->
                        val entries = rssiHistory.mapIndexed { index, rssi ->
                            Entry(index.toFloat(), rssi.toFloat())
                        }
                        val dataSet = LineDataSet(entries, "RSSI History").apply {
                            color = Color.BLUE
                            valueTextColor = Color.BLACK
                            lineWidth = 2f
                            setDrawCircles(true)
                            circleColors = listOf(Color.BLUE)
                            setDrawValues(false)
                        }
                        chart.data = LineData(dataSet)
                        chart.invalidate()
                    }
                )
            }
        )
    }
}
