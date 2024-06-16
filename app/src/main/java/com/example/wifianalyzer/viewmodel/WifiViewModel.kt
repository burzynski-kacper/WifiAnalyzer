package com.example.wifianalyzer.viewmodel

import android.content.Context
import android.net.wifi.WifiManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wifianalyzer.model.Networks
import kotlin.math.pow
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wifianalyzer.data.NetworkItem
import com.example.wifianalyzer.data.NetworkRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class WifiViewModel(private val repository: NetworkRepository): ViewModel() {
    private val _wifiNetworks = MutableLiveData<List<Networks>>()
    val wifiNetworks: LiveData<List<Networks>> get() = _wifiNetworks

    fun loadWifiNetworks(context: Context) {
        Log.d("WifiViewModel", "Loading WiFi networks")
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiList = wifiManager.scanResults.map { scanResult ->
            val distance = calculateDistance(scanResult.level, scanResult.frequency)
            Networks(
            bssid = scanResult.BSSID,
            ssid = scanResult.SSID,
            rssi = scanResult.level,
            linkSpeed = wifiManager.connectionInfo.linkSpeed,
            frequency = scanResult.frequency,
            distance = distance
        ) }
        _wifiNetworks.value = wifiList
        saveNetworkReadings(wifiList)
    }
    private fun calculateDistance(rssi: Int, frequency: Int): Double {
        val exp = (27.55 - (20 * kotlin.math.log10(frequency.toDouble())) + kotlin.math.abs(rssi)) / 20.0
        return 10.0.pow(exp)
    }

    fun saveNetworkReadings(networks: List<Networks>){
        viewModelScope.launch {
            networks.forEach { network ->
                val existingNetwork = repository.getItemStreamByBssid(network.bssid).firstOrNull()
                if (existingNetwork != null) {
                    val updatedRssiHistory = existingNetwork.rssiHistory.toMutableList()
                    val updatedDistanceHistory = existingNetwork.distanceHistory.toMutableList()

                    if (updatedRssiHistory.isEmpty() || updatedRssiHistory.last() != network.rssi) {
                        updatedRssiHistory.add(network.rssi)
                    }

                    if (updatedDistanceHistory.isEmpty() || updatedDistanceHistory.last() != network.distance) {
                        updatedDistanceHistory.add(network.distance)
                    }

                    val updatedReading = existingNetwork.copy(
                        rssiHistory = updatedRssiHistory,
                        distanceHistory = updatedDistanceHistory
                    )
                    repository.updateItem(updatedReading)
                } else {
                    val newReading = NetworkItem(
                        bssid = network.bssid,
                        ssid = network.ssid,

                        rssi = network.rssi,
                        linkSpeed = network.linkSpeed,
                        frequency = network.frequency,
                        distance = network.distance,
                        rssiHistory = listOf(network.rssi),
                        distanceHistory = listOf(network.distance)
                    )
                    repository.insertItem(newReading)
                }
            }
        }
    }

    fun getNetworkItemByBssid(bssid: String): LiveData<NetworkItem?> {
        return repository.getItemStreamByBssid(bssid).asLiveData()
    }

    fun deleteItemByBssid(bssid: String) {
        viewModelScope.launch {
            repository.deleteItemByBssid(bssid)
        }
    }
}