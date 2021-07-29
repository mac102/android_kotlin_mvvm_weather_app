package com.bcpl.forecastmvvm.data.provider

import com.bcpl.forecastmvvm.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPrefferedLocationString() : String
}