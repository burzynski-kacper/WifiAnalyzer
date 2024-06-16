package com.example.wifianalyzer.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters

@Database(entities = [NetworkItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun networkDao(): NetworkDao

    companion object {
        @Volatile
        private var Instace: AppDB? = null

        fun getDatabase(context: Context): AppDB {
            return Instace ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDB::class.java,
                    "wifi_database"
                ).build()
                    .also { Instace = it }
            }
        }
    }
}