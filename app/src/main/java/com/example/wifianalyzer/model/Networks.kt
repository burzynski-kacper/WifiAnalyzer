package com.example.wifianalyzer.model

data class Networks (
    val bssid: String,
    val ssid: String,
    val rssi: Int,
    val linkSpeed: Int,
    val frequency: Int,
    val distance: Double
)