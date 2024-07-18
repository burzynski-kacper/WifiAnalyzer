# WiFiAnalyzer

## Overview

WiFiAnalyzer is an Android application designed to scan and analyze WiFi networks. The app provides detailed information about available WiFi networks, including signal strength (RSSI), link speed, frequency, and estimated distance. 
It also allows users to view historical data and visualize RSSI trends over time.

## Features

- **Scan WiFi Networks**: Automatically scans and lists available WiFi networks.
- **Network Details**: Displays detailed information about each network, including BSSID, SSID, RSSI, link speed, frequency, and estimated distance.
- **Historical Data**: Stores historical data for each network and allows users to view it.
- **RSSI Chart**: Visualizes RSSI trends over time for selected networks.
- **User-Friendly Interface**: Provides a clean and intuitive user interface using Jetpack Compose.

## Technologies Used

- **Kotlin**: For the main programming language.
- **Jetpack Compose**: For building the user interface.
- **Room**: For local database storage.
- **Firebase**: For potential cloud storage and authentication.
- **MPAndroidChart**: For charting and visualizing data.

## Permissions

The app requires the following permissions:
- `android.permission.INTERNET`
- `android.permission.ACCESS_WIFI_STATE`
- `android.permission.ACCESS_NETWORK_STATE`
- `android.permission.ACCESS_FINE_LOCATION`
- `android.permission.CHANGE_WIFI_STATE`
- `android.permission.ACCESS_COARSE_LOCATION`

## Code Structure

- **data/**: Contains data access objects (DAOs), database definitions, and type converters.
  - `NetworkDao.kt`: DAO for accessing network data.
  - `AppDB.kt`: Database configuration.
  - `Converters.kt`: Type converters for Room.
  - `OfflineNetworkRepository.kt`: Repository for offline data handling.
- **model/**: Contains data models.
  - `Networks.kt`: Data model for WiFi networks.
- **ui/**: Contains UI components and screens.
  - `MainActivity.kt`: Main activity of the app.
  - `WifiAnalyzerApp.kt`: Composable function for the main app structure.
  - `WifiList.kt`: Composable function for displaying the list of WiFi networks.
  - `WifiRssiChartScreen.kt`: Composable function for displaying the RSSI chart.
  - `WifiHistoryScreen.kt`: Composable function for displaying historical data.
  - `theme/`: Contains theme definitions.
- **viewmodel/**: Contains ViewModel classes.
  - `WifiViewModel.kt`: ViewModel for managing WiFi network data.
  - `WifiViewModelFactory.kt`: Factory for creating instances of `WifiViewModel`.

