package com.bcpl.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bcpl.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.bcpl.forecastmvvm.data.network.response.FutureWeatherResponse
import com.bcpl.forecastmvvm.internal.NoConnectivityException

const val FORECAST_DAYS_COUNT = 7

class WeatherNetworkDatSourceImpl(
    private val apixuWeatherApiService: ApixuWeatherApiService
) : WeatherNetworkDatSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()

    // live data nie może być modyfikowane, stąd cast MutableList na livedata w celu nikniecia udostpenienia mozliwości działań na liście do klienta
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchCurrentWeather = apixuWeatherApiService
                .getCurrentWeather(location, languageCode)
                .await()
            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connextivity", "No internet connection")
        }
    }

    override suspend fun fetchFutureWeather(location: String, languageCode: String) {
        try {
            val fetchFutureWeather = apixuWeatherApiService
                .getFutureWeather(location, FORECAST_DAYS_COUNT, languageCode)
                .await()
            _downloadedFutureWeather.postValue(fetchFutureWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connextivity", "No internet connection")
        }
    }
}