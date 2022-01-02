package com.kth.weatherapp.model.utils

object Constants {

    const val API_URL = "https://api.openweathermap.org/data/2.5/weather?"

    // TODO should NEVER be publicly versioned or should be hidden (sign up to openWeathermap.com and replace this empty string with your key :-) )
    const val API_KEY = ""

    const val API_ID_PARAM = "&APPID="
    const val API_CITY_PARAM = "q="
    const val API_METRIC_SYS_PARAM = "&units=metric"
    const val API_LANG_OUTPUT = "&lang=fr"


    // ERROR CODES/MESSAGES
    const val IO_EXCEPTION_CODE = "IOException"
    const val IO_EXCEPTION_MSG = "An error occured during API Calls"
}