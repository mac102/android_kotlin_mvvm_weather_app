package com.bcpl.forecastmvvm

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.bcpl.forecastmvvm.data.db.ForecastDatabase
import com.bcpl.forecastmvvm.data.network.*
import com.bcpl.forecastmvvm.data.provider.LocationProvider
import com.bcpl.forecastmvvm.data.provider.LocationProviderImpl
import com.bcpl.forecastmvvm.data.provider.UnitProvider
import com.bcpl.forecastmvvm.data.provider.UnitProviderImpl
import com.bcpl.forecastmvvm.data.repository.ForecastRepository
import com.bcpl.forecastmvvm.data.repository.ForecastRepositoryImpl
import com.bcpl.forecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.bcpl.forecastmvvm.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import com.bcpl.forecastmvvm.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) } // instance z andoridXmodule by kodein
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDatSource>() with singleton { WeatherNetworkDatSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
        bind() from factory { detailDate: LocalDate -> FutureDetailWeatherViewModelFactory(detailDate, instance(), instance())}
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false) // def values when app starts first time
    }
}