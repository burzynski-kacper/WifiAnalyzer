package com.example.wifianalyzer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wifianalyzer.R
import com.example.wifianalyzer.viewmodel.WifiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiAnalyzerApp(wifiViewModel: WifiViewModel) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.wifi_analyzer),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(73, 20, 128)
                )
            )
        },
        content = { paddingValues ->
            NavHost(navController, startDestination = "wifiList") {
                composable("wifiList") {
                    WifiList(paddingValues, wifiViewModel, { bssid ->
                        navController.navigate("wifiHistory/$bssid")
                    }, { bssid ->
                        navController.navigate("wifiRssiChart/$bssid")
                    })
                }
                composable(
                    "wifiHistory/{bssid}",
                    arguments = listOf(
                        navArgument("bssid") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val bssid = backStackEntry.arguments?.getString("bssid") ?: ""
                    WifiHistoryScreen(bssid, wifiViewModel)
                }
                composable(
                    "wifiRssiChart/{bssid}",
                    arguments = listOf(
                        navArgument("bssid") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val bssid = backStackEntry.arguments?.getString("bssid") ?: ""
                    WifiRssiChartScreen(bssid, wifiViewModel)
                }
            }
        }
    )
}