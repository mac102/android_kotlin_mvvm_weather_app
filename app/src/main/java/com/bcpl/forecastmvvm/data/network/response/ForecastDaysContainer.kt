package com.bcpl.forecastmvvm.data.network.response

import com.bcpl.forecastmvvm.data.db.entity.FutureWeatherEntry
import com.google.gson.annotations.SerializedName

data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)