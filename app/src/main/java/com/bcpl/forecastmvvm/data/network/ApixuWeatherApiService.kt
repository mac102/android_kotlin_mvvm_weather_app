package com.bcpl.forecastmvvm.data.network

import com.bcpl.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.bcpl.forecastmvvm.data.network.response.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "xxxxx"

interface ApixuWeatherApiService {
    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") languageCode: String = "en"
    ): Deferred<CurrentWeatherResponse>

    @GET("forecast.json")
    fun getFutureWeather(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") languageCode: String = "en",
    ) : Deferred<FutureWeatherResponse>

    companion object {
        // invoke == wywołanie serwisu: ApixuWeatherApiService()
        // bez tego, można nazwać funkcje np. start, ale wtedy wywołanie wyglda tak: ApixuWeatherApiService.start()
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApixuWeatherApiService {
            // dodaje do każdego zapytania wartość klucza API_KEY
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://apixu.com/v1")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())  // async calls
                .addConverterFactory(GsonConverterFactory.create())  // używaj gsona
                .build()
                .create(ApixuWeatherApiService::class.java)
        }
    }
}