package com.bcpl.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.bcpl.forecastmvvm.data.provider.UnitProvider
import com.bcpl.forecastmvvm.data.repository.ForecastRepository
import com.bcpl.forecastmvvm.internal.lazyDeffered
import com.bcpl.forecastmvvm.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeffered {
        forecastRepository.getFutureWeatherByDate(detailDate, super.isMetricUnit)
    }
}