package com.example.wifianalyzer.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "networks")
data class NetworkItem(
    @PrimaryKey
    val bssid: String,
    val ssid: String,
    val rssi: Int,
    val linkSpeed: Int,
    val frequency: Int,
    val distance: Double,
    val rssiHistory: List<Int> = listOf(),
    val distanceHistory: List<Double> = listOf()
)

