package com.example.weatherapplication

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object WeatherHelper {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .build().create(ApiInterface::class.java)

    fun fetchWeatherData(cityName: String, apiKey: String, callback: (WeatherApp?) -> Unit) {
        val response = retrofit.getweatherdata(cityName, apiKey, "metric")
        response.enqueue(object : Callback<WeatherApp> {
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    callback(weatherData)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun formatDay(timestamp: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}
