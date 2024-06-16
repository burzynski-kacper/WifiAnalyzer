package com.example.wifianalyzer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NetworkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(networkItem: NetworkItem)

    @Update
    suspend fun update(networkItem: NetworkItem)

    @Delete
    suspend fun delete(networkItem: NetworkItem)

    @Query("SELECT * FROM networks WHERE bssid = :bssid")
    fun getItem(bssid: String): Flow<NetworkItem>

    @Query("SELECT * FROM networks ORDER BY bssid ASC")
    fun getAll(): Flow<List<NetworkItem>>

    @Query("SELECT * FROM networks WHERE bssid = :bssid")
    fun getItemByBssid(bssid: String): Flow<NetworkItem>

    @Query("DELETE FROM networks WHERE bssid = :bssid")
    suspend fun deleteRow(bssid: String)
}