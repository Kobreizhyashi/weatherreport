package com.kth.weatherapp.data.webServices

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody

class WeatherHttpClient {

   private val client = OkHttpClient()

     fun call(url : String): String? {

        val request = Request.Builder().url(url).build();

        try {
            val res = client.newCall(request).execute()
            return res.body?.string()
        } catch(e: Exception) {
            Log.e("ERROR", "" + e.printStackTrace())
            throw e
        }
    }
}