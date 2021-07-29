package com.bcpl.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.bcpl.forecastmvvm.data.provider.UnitProvider
import com.bcpl.forecastmvvm.data.repository.ForecastRepository
import com.bcpl.forecastmvvm.internal.UnitSystem
import com.bcpl.forecastmvvm.internal.lazyDeffered
import com.bcpl.forecastmvvm.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeffered {
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }
}