package com.kth.weatherapp.model.repository

import com.google.gson.GsonBuilder
import com.kth.weatherapp.model.data.City
import com.kth.weatherapp.model.data.WeatherReport
import com.kth.weatherapp.model.utils.Constants
import com.kth.weatherapp.model.network.WeatherHttpClient
import java.lang.Exception

class WeatherRepository {


    // TODO : Singleton here
    private val httpClient = WeatherHttpClient()

    // TODO: Should be stored in DB
    // TODO: Missing city in the statement to reach 60 seconds with 1 per 10 seconds starting from 0
    private val cityList = listOf(
        City(id = 0, name = "Rennes", cityKey = "Rennes,fr", isInBrittany = true),
        City(id = 1, name = "Paris", cityKey = "Paris,fr"),
        City(id = 2, name = "Nantes", cityKey = "Nantes,fr", isInBrittany = true),
        City(id = 3, name = "Bordeaux", cityKey = "Bordeaux,fr"),
        City(id = 4, name = "Lyon", cityKey = "Lyon,fr"),
    )

    // TODO: Should be stored in DB
    private val weatherList = mutableListOf<WeatherReport>()

    fun clearList() = weatherList.clear()

    fun fetchData(cityId: Int) {
        try {
            buildUrl(cityId)?.let { url ->
                val responseBody = httpClient.call(url)
                responseBody?.let {
                    val gson = GsonBuilder().create()
                    gson.fromJson(it, WeatherReport::class.java).let { wr ->
                        weatherList.add(wr)
                    }
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun getWeatherReports(): List<WeatherReport> = weatherList

    private fun buildUrl(cityId: Int): String? {
        // TODO : I should better call a DAO fun() to get the city I want (or the next city)
        val city = cityList.firstOrNull { it.id == cityId }
        city?.let {
            // TODO : Maybe this could be better with an URI builder (Apache URIBuilder F.E)
            return "${Constants.API_URL}${Constants.API_CITY_PARAM}${it.cityKey}${Constants.API_METRIC_SYS_PARAM}${Constants.API_LANG_OUTPUT}${Constants.API_ID_PARAM}${Constants.API_KEY}"
        }
        return null

    }
}