package com.kth.weatherapp.data

import com.google.gson.GsonBuilder
import com.kth.weatherapp.data.entities.City
import com.kth.weatherapp.data.entities.WeatherReport
import com.kth.weatherapp.data.utils.Constants
import com.kth.weatherapp.data.webServices.WeatherHttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class WeatherRepository {

    // TODO : Singleton here
    private val httpClient = WeatherHttpClient()

    private val cityList = mutableListOf<City>()


    init {
        // TODO: Should be stored inside Room and fetched when needed
        cityList.add(City(id = 0, name = "Rennes", cityKey = "Rennes,fr", isInBrittany = true))
        cityList.add(City(id = 1, name = "Paris", cityKey = "Paris,fr"))
        cityList.add(City(id = 2, name = "Nantes", cityKey = "Nantes,fr", isInBrittany = true))
        cityList.add(City(id = 3, name = "Bordeaux", cityKey = "Bordeaux,fr"))
        cityList.add(City(id = 4, name = "Lyon", cityKey = "Lyon,fr"))
    }

    suspend fun getWeather(cityId: Int): WeatherReport? {
        try {
            buildUrl(cityId)?.let {
                 withContext(Dispatchers.IO) {
                    val responseBody = httpClient.call(it)
                     return@withContext responseBody?.let {
                         val gson = GsonBuilder().create()
                         gson.fromJson(it, WeatherReport::class.java)
                     }
                }
            }
        } catch (e: Exception) {
            throw e
        }
        return null
    }

    fun buildUrl(cityId: Int): String? {
        // TODO : I shall call a DAO fun to get the city I want (or the next city)
        val city = cityList.firstOrNull { it.id == cityId }
        city?.let {
            // TODO : Maybe this could be better with an URI builder (Apache URIBuilder F.E)
            return "${Constants.API_URL}${Constants.API_CITY_PARAM}${it.cityKey}${Constants.API_ID_PARAM}${Constants.API_KEY}"
        }
        return null

    }
}