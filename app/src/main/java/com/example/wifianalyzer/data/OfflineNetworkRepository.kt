package com.example.wifianalyzer.data

import kotlinx.coroutines.flow.Flow

class OfflineNetworkRepository(private val networkDao: NetworkDao) : NetworkRepository {
    override fun getAllItemsStream(): Flow<List<NetworkItem>> = networkDao.getAll()

    override fun getItemStream(bssid: String): Flow<NetworkItem?> = networkDao.getItem(bssid)

    override fun getItemStreamByBssid(bssid: String): Flow<NetworkItem> = networkDao.getItemByBssid(bssid)
    override suspend fun deleteItemByBssid(bssid: String) = networkDao.deleteRow(bssid)

    override suspend fun insertItem(item: NetworkItem) = networkDao.insert(item)

    override suspend fun deleteItem(item: NetworkItem) = networkDao.delete(item)

    override suspend fun updateItem(item: NetworkItem) = networkDao.update(item)
}