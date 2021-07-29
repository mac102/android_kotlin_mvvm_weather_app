package com.bcpl.forecastmvvm.data.network

import androidx.lifecycle.LiveData
import com.bcpl.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.bcpl.forecastmvvm.data.network.response.FutureWeatherResponse

interface WeatherNetworkDatSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )

    suspend fun fetchFutureWeather(
        location: String,
        languageCode: String
    )
}