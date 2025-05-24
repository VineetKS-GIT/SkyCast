package com.example.weatherapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getweatherdata(
        @Query("q") city:String,
        @Query("appid") appid:String,
        @Query("units")units:String

    ) : Call<WeatherApp>


    @GET("data/2.5/forecast")
    fun getForecastWeather(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units: String,

        ) : Call<ForecastWeatherApi>

    @GET("geo/1.0/direct")
    fun searchCities(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): Call<List<CitySearchResponse.CitySearchResponseItem>>



}


