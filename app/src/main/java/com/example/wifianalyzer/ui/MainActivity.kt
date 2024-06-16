package com.example.wifianalyzer.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.wifianalyzer.ui.theme.WifiAnalyzerTheme
import com.example.wifianalyzer.viewmodel.WifiViewModel
import androidx.lifecycle.lifecycleScope
import com.example.wifianalyzer.data.AppDB
import com.example.wifianalyzer.data.OfflineNetworkRepository
import com.example.wifianalyzer.viewmodel.WifiViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val wifiViewModel: WifiViewModel by viewModels {
        val database = AppDB.getDatabase(this)
        val repository = OfflineNetworkRepository(database.networkDao())
        WifiViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WifiAnalyzerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WifiAnalyzerApp(wifiViewModel)
                }
            }
        }

        requestPermissions()

        lifecycleScope.launch {
            while (true) {
                wifiViewModel.loadWifiNetworks(this@MainActivity)
                delay(10000L)
            }
        }
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE
        )

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.all { it.value }) {
                // Załaduj dane sieci WiFi
                wifiViewModel.loadWifiNetworks(this)
            } else {
                // Obsłuż przypadek, gdy uprawnienia nie zostały przyznane
            }
        }

        if (permissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }) {
            // Załaduj dane sieci WiFi
            wifiViewModel.loadWifiNetworks(this)
        } else {
            requestPermissionLauncher.launch(permissions)
        }
    }

}
