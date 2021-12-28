package com.kth.weatherapp.data.webServices

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.math.log

class WeatherHttpClient {

   private val client = OkHttpClient()

    suspend fun call(url : String) {

        val request = Request.Builder().url(url).build();

        try {
            val res = client.newCall(request).execute()
            res.body
            Log.d("Value", "" + res.body.toString())
        } catch(e: Exception) {
            Log.e("ERROR", "" + e.printStackTrace())
            throw e
        }
    }
}