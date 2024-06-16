package com.example.wifianalyzer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifianalyzer.R

@Composable
fun WifiNetworkItem(
    ssid: String,
    rssi: Int,
    linkSpeed: Int,
    frequency: Int,
    distance: Double,
    onHistoryClick: () -> Unit,
    onRssiChartClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = ssid, fontWeight = FontWeight.Bold, fontSize = 26.sp)
        Text(text = "RSSI: $rssi dBm", fontSize = 18.sp)
        Text(text = "Link Speed: $linkSpeed Mbps", fontSize = 18.sp)
        Text(text = "Frequency: $frequency MHz", fontSize = 18.sp)
        Text(text = "Distance: %.2f meters".format(distance), fontSize = 18.sp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = onHistoryClick , Modifier.size(width = 140.dp, height = 40.dp)) {
                Text(text = stringResource(R.string.historyButton))
            }
            Button(onClick = onRssiChartClick, Modifier.size(width = 140.dp, height = 40.dp)) {
                Text(text = stringResource(R.string.rssiButton))
            }
        }
    }
}