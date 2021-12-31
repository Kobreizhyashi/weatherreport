package com.kth.weatherapp.ui.features.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kth.weatherapp.model.repository.WeatherRepository
import com.kth.weatherapp.model.data.WeatherReport
import com.kth.weatherapp.ui.utils.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception
import java.util.*

// (TODO) Should be injected
class WeatherActivityViewModel : ViewModel() {

    // (TODO) Injected
    private val repository = WeatherRepository()

    val progressBarValue = MutableStateFlow(0f)
    val progressBarText = MutableStateFlow("0%")
    val progressMessage = MutableStateFlow("")
    val isTimerOver = MutableStateFlow(false)

    private var messagesIndex = 0

    // TODO : put that in strings.xml maybe?
    val messages = listOf(
        "Nous téléchargeons les données…",
        "C’est presque fini…",
        "Plus que quelques secondes avant d’avoir le résultat…",
    )

    val weatherReportList = MutableStateFlow<List<WeatherReport>>(listOf())

    private var citiesIndex = 0
    private var ticker = 0

    private fun resetMagicValues() {
        repository.clearList()
        progressBarValue.value = .0f
        progressBarText.value = "0%"
        isTimerOver.value = false
        ticker = 0
        citiesIndex = 0
    }

    // TODO : Those next logic handling methods should be in a service and this ViewModel should only prepare and expose data for our UI

    fun fetchData() {
        resetMagicValues()
        val timer = Timer()
        // TODO : after few hours and time spent reading the kata's text, It appears that 2 Timer.schedules should be better, but problematic.
        // If I had more time I would take an other choice with the ScheduledThreadPoolExecutor API for a cleaner code too (avoid magicNumbers etc..)
        timer.schedule(object : TimerTask() {
            override fun run() {
                ticker++
                //TODO : 'If/else' waterfall can maybe be improved
                if (ticker % 6 == 0) {
                    if (ticker == 60) {
                        handleTimerOver()
                    } else {
                        updateMessage()
                    }
                }
                if (ticker % 10 == 0 && !isTimerOver.value) {
                    callApi()
                }
            }

        }, 0, 1000)
    }

    fun handleTimerOver() {
        viewModelScope.launch {
            weatherReportList.emit(repository.getWeatherReports())
            isTimerOver.emit(true)
        }
    }

    fun updateMessage() {
        viewModelScope.launch {
            messagesIndex++
            if (messagesIndex == messages.size) {
                messagesIndex = 0
            }
            progressMessage.emit(messages[messagesIndex])
        }
    }


    fun callApi() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.fetchData(citiesIndex)

             withContext(Dispatchers.Main) {
                 progressBarValue.emit(progressBarValue.value.plus(0.2f))
                 // TODO : I could do a simple toInt() to remove the ".0" but sometimes it's not the better solution, In the case of StateFlow, it's really not. It breaks the event loop.
                 // TODO : And I like to work with strings when I have to return a string...
                 progressBarText.emit(
                     (progressBarValue.value * 100).toString()
                         .substringBefore('.') + Constants.PERCENT
                 )
             }
                citiesIndex++
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.stackTraceToString())
            throw e
        }
    }

}