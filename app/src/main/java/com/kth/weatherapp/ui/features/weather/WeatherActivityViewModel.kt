package com.kth.weatherapp.ui.features.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kth.weatherapp.data.WeatherRepository
import com.kth.weatherapp.data.entities.WeatherReport
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception
import java.util.*

// (TODO) Should be injected
class WeatherActivityViewModel : ViewModel() {

    private val repository = WeatherRepository()

    val progressValue = MutableStateFlow(0f)
    val timerOver = MutableStateFlow(false)

    var weatherReportList = mutableListOf<WeatherReport>()

    private var citiesIndex = 0
    private var ticker = 0

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
                repository.getWeather(citiesIndex)?.let { weatherReportList.add(it) }
                progressValue.emit(progressValue.value.plus(0.2f))
                citiesIndex++
            } catch (e: Exception) {
                Log.e("ERROR", e.stackTraceToString())
                throw e
            }
        }
    }
}