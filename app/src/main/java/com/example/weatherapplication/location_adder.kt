package com.example.weatherapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.ActivityLocationAdderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class location_adder : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter

    private val binding1: ActivityLocationAdderBinding by lazy {
        ActivityLocationAdderBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding1.root)

        adapter = CityAdapter(emptyList()) { cityName ->
            fetchWeatherData(cityName)
            val changepage = Intent(this@location_adder, MainActivity::class.java)
            changepage.putExtra("cityName", cityName)
            startActivity(changepage)
        }

        searchCity()
    }

    private fun fetchWeatherData(cityName: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)

        val response = retrofit.getweatherdata(cityName, "8f20f2bb596b889a9da4ab62b00f19cb", "metric")
        response.enqueue(object : Callback<WeatherApp> {
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    // Handle the data as needed within this activity or pass it to MainActivity
                    val changepage = Intent(this@location_adder, MainActivity::class.java).apply {
                        putExtra("cityName", cityName)
                        putExtra("temperature", responseBody.main.temp.toString())
                        putExtra("humidity", responseBody.main.humidity)
                        putExtra("wind", responseBody.wind.speed)
                        putExtra("sunrise", responseBody.sys.sunrise)
                        putExtra("sunset", responseBody.sys.sunset)
                        putExtra("seaLevel", responseBody.main.sea_level)
                        putExtra("condition", responseBody.weather.firstOrNull()?.main ?: "unknown")
                        putExtra("maxtemp", responseBody.main.temp_max)
                        putExtra("mintemp", responseBody.main.temp_min)
                    }
                    startActivity(changepage)
                }
            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                // Handle the failure case
            }
        })
    }

    private fun searchCity() {
        val searchView = binding1.search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotBlank()) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


}
