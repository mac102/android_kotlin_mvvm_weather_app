package com.bcpl.forecastmvvm.data.network.response

import com.bcpl.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.bcpl.forecastmvvm.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val location: WeatherLocation,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)