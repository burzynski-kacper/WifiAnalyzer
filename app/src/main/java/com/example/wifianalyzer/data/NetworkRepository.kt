package com.example.wifianalyzer.data

import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun getAllItemsStream(): Flow<List<NetworkItem>>
    fun getItemStream(bssid: String): Flow<NetworkItem?>
    fun getItemStreamByBssid(bssid: String): Flow<NetworkItem>
    suspend fun deleteItemByBssid(bssid: String)
    suspend fun insertItem(item: NetworkItem)
    suspend fun deleteItem(item: NetworkItem)
    suspend fun updateItem(item: NetworkItem)
}