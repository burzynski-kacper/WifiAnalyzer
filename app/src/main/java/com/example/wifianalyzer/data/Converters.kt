package com.example.wifianalyzer.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromIntList(value: List<Int>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromDoubleList(value: List<Double>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun toDoubleList(value: String): List<Double> {
        return value.split(",").map { it.toDouble() }
    }
}