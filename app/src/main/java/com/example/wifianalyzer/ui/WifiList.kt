package com.example.wifianalyzer.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.wifianalyzer.viewmodel.WifiViewModel


@Composable
fun WifiList(paddingValues: PaddingValues, wifiViewModel: WifiViewModel, onHistoryClick: (String) -> Unit, onRssiChartClick: (String) -> Unit) {
    val wifiNetworks = wifiViewModel.wifiNetworks.observeAsState(emptyList())

    LazyColumn(
        contentPadding = paddingValues
    ) {
        items(wifiNetworks.value) { network ->
            WifiNetworkItem(
                ssid = network.ssid,
                rssi = network.rssi,
                linkSpeed = network.linkSpeed,
                frequency = network.frequency,
                distance = network.distance,
                onHistoryClick = { onHistoryClick(network.bssid) },
                onRssiChartClick = { onRssiChartClick(network.bssid)}
            )
        }
    }
}