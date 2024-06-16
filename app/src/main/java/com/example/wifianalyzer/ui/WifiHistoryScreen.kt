package com.example.wifianalyzer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wifianalyzer.R
import com.example.wifianalyzer.viewmodel.WifiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiHistoryScreen(
    bssid: String,
    wifiViewModel: WifiViewModel = viewModel(),
) {
    val networkItem = wifiViewModel.getNetworkItemByBssid(bssid).observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.wifi_history))
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    networkItem.value?.let { item ->
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.ssid,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 26.sp
                                )
                            }
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = stringResource(R.string.rssi_history),
                                        fontSize = 22.sp,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )

                                    item.rssiHistory.forEach { rssi ->
                                        Text(
                                            text = "$rssi dBm",
                                            fontSize = 18.sp,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = stringResource(R.string.distance_history),
                                        fontSize = 22.sp,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                    item.distanceHistory.forEach { distance ->
                                        Text(
                                            text = "%.2f m".format(distance),
                                            fontSize = 18.sp,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                            }
                        }
                    } ?: item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize(),
                            ){
                            Text(
                                text = stringResource(R.string.no_data_available),
                                color = Color.LightGray
                            )
                        }
                    }

                }
                Button(
                    onClick = { wifiViewModel.deleteItemByBssid(bssid) },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text(text = "Wyczyść dane")
                }
            }
        }
    )
}
