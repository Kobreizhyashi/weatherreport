package com.kth.weatherapp.data.entities

enum class Weather() {
    CLOUDY,
    VERY_CLOUDY,
    SUNNY,
    RAINY
}

class CityWeather(
    val name: String,
    val coordinates: String,
    var temperatures: String = "",
    // It's always sunny in brittany :-)
    var weather: Weather = Weather.SUNNY
)
