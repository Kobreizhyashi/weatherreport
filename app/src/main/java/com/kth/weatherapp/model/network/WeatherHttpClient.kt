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
        try {
            // TODO : I chose OkHttp3 to save time
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            result = response.body?.string()
        } catch (e: IOException) {
            // TODO : Firebase call here
            Log.e(Constants.IO_EXCEPTION_CODE, Constants.IO_EXCEPTION_MSG, e.cause)
            throw e
        }
        return result

    }
}