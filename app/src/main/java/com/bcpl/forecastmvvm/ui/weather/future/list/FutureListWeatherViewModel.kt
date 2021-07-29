package com.bcpl.forecastmvvm.ui.weather.future.list

import com.bcpl.forecastmvvm.data.provider.UnitProvider
import com.bcpl.forecastmvvm.data.repository.ForecastRepository
import com.bcpl.forecastmvvm.internal.lazyDeffered
import com.bcpl.forecastmvvm.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weatherEntries by lazyDeffered {
        forecastRepository.getFutureWeatherList(LocalDate.now(), super.isMetricUnit)
    }
}