package com.kth.weatherapp.data

import com.kth.weatherapp.data.entities.CityWeather
import com.kth.weatherapp.data.webServices.WeatherHttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class WeatherRepository {

    private val httpClient = WeatherHttpClient()
    suspend fun getWeather(city: CityWeather) {
        try {
            return withContext(Dispatchers.IO) {

                val JSON_KEY = "json";
                val url =
                    "https://api.meteomatics.com/2021-12-28T00:00:00Z:PT1H/t_2m:C,precip_1h:mm/${city.coordinates}/$JSON_KEY"
                httpClient.call(url)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}