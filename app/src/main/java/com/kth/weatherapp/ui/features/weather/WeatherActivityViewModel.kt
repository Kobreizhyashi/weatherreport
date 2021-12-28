package com.kth.weatherapp.ui.features.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kth.weatherapp.data.WeatherRepository
import com.kth.weatherapp.data.entities.CityWeather
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception
import java.util.*

// (TODO) Should be injected
class WeatherActivityViewModel : ViewModel() {


    val repository = WeatherRepository()
    val progressValue = MutableStateFlow(0f)
    val timerOver = MutableStateFlow(false)

    var citiesIndex = 0
    var ticker = 0


    private val citiesMap = mutableListOf<CityWeather>()

    init {
        // TODO: Should be in DB..
        citiesMap.add(CityWeather(name = "Rennes", coordinates = "1.677793,48.117266"))
        citiesMap.add(CityWeather(name = "Paris", coordinates = "2.352222,48.856614"))
        citiesMap.add(CityWeather(name = "Nantes", coordinates = "-1.553621,47.218371"))
        citiesMap.add(CityWeather(name = "Bordeaux", coordinates = "44.837789,-0.57918"))
        citiesMap.add(CityWeather(name = "Lyon", coordinates = "4.835659,45.764043"))
    }

    private fun resetMagicValues() {
        progressValue.value = 0f
        timerOver.value = false
        ticker = 0
        citiesIndex = 0
    }

    fun fetchData() {
        resetMagicValues()
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                ticker++

                //TODO : 'If/else' waterfall can maybe be improved
                /// No need to launch viewModelScope if [ticker] is not ready
                if (progressValue.value < 1f) {
                    if (ticker % 10 == 0) {
                        callApi()
                    }
                    if (ticker % 6 == 0) {
                        if (ticker == 60) {
                            timer.purge()
                            handleTimerOver()
                        } else {
                            updateMessage()
                        }
                    }
                }


            }
        }, 0, 1000)
    }

    fun handleTimerOver() {
        viewModelScope.launch {
            timerOver.emit(true)
            Log.d("Timer over !", "TIMER OVER ")
        }
    }

    fun updateMessage() {
        viewModelScope.launch {
            Log.d("Message sent !", "SENT ")
        }
    }


    fun callApi() {
        viewModelScope.launch {
            try {
                citiesMap[citiesIndex].let {
                    repository.getWeather(it)
                    progressValue.emit(progressValue.value.plus(0.2f))
                    citiesIndex++
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.localizedMessage)
                throw e
            }
        }
    }
}