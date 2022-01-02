package com.kth.weatherapp.model.network

import android.util.Log
import com.kth.weatherapp.model.utils.Constants
import okhttp3.*
import java.io.IOException

// TODO: Singleton mandatory here..
class WeatherHttpClient {

    private val client = OkHttpClient()
    fun call(url: String): String? {
        var result: String? = null
            // TODO : I chose OkHttp3 to save time
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                result = response.body?.string()
            } else {
                throw IOException("Error during API request")
            }

        return result

    }
}