
package com.example.weatherapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.widget.ImageButton
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import com.airbnb.lottie.LottieAnimationView


import com.example.weatherapplication.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale




//8f20f2bb596b889a9da4ab62b00f19cb

class MainActivity : AppCompatActivity() {

    private val  binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var lottieanimation: LottieAnimationView
    private lateinit var animatedsunset: LottieAnimationView
    private lateinit var animatedsunrise: LottieAnimationView
    private lateinit var animatedwind: LottieAnimationView
    private lateinit var animatedsea: LottieAnimationView
    private lateinit var animatedhumidity: LottieAnimationView
    private lateinit var addButton:ImageButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val cityName = intent.getStringExtra("cityName")?: "Jaipur"
        if (!cityName.isNullOrEmpty()) {
            val todayTextView = findViewById<TextView>(R.id.today1)
            todayTextView.text = cityName
            fetchWeatherData(cityName) // Call fetchWeatherData with the cityName
//            fetchForecastWeatherData(cityName)
        }

        lottieanimation = findViewById(R.id.lottieanimation)
        animatedsunset = findViewById(R.id.animatedsunset)
        animatedsunrise = findViewById(R.id.animated_sunrise)
        animatedwind = findViewById(R.id.animated_wind)
        animatedsea = findViewById(R.id.animated_sea)
        animatedhumidity = findViewById(R.id.animated_humidity)

        addButton = findViewById(R.id.addbtn)


        lottieanimation.playAnimation()
        animatedsunset.playAnimation()
        animatedsunrise.playAnimation()
        animatedwind.playAnimation()
        animatedsea.playAnimation()
        animatedhumidity.playAnimation()

        addButton.setOnClickListener {
            startActivity(Intent(this, location_adder::class.java))
        }



    }

//    private fun fetchForecastWeatherData(cityName: String) {
//        val retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://api.openweathermap.org/data/2.5/")
//            .build().create(ApiInterface::class.java)
//
//        val response = retrofit.getForecastWeather(cityName, "8f20f2bb596b889a9da4ab62b00f19cb", "metric")
//        response.enqueue(object : Callback<ForecastWeatherApi> {
//            @SuppressLint("SetTextI18n")
//            override fun onResponse(call: Call<ForecastWeatherApi>, response: Response<ForecastWeatherApi>) {
//                val responseBody = response.body()
//                if (response.isSuccessful && responseBody != null) {
//                    val forecastList = responseBody.list
//                    if (forecastList != null && forecastList.isNotEmpty()) {
//                        val firstForecast = forecastList[0] // Get the first forecast entry
//                        }
//                    }else{
//                    Log.e("ForecastWeather", "Error: ${response.code()} ${response.message()}")
//                }
//
//            }
//
//            override fun onFailure(call: Call<ForecastWeatherApi>, t: Throwable) {
//                Log.e("ForecastWeather", "Error fetching forecast weather", t)
//            }
//        })
//    }

    private fun setStatusBarColor(colorResId: Int) {
        window.statusBarColor = resources.getColor(colorResId, theme)
    }


    private fun fetchWeatherData(cityName:String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)

        val response = retrofit.getweatherdata(cityName, "8f20f2bb596b889a9da4ab62b00f19cb", "metric")
        response.enqueue(object : Callback<WeatherApp> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responsebody = response.body()
                if (response.isSuccessful && responsebody != null) {
                    val temperature = responsebody.main.temp.toString()
                    val humidity = responsebody.main.humidity
                    val wind = responsebody.wind.speed
                    val sunrise = responsebody.sys.sunrise
                    val sunset = responsebody.sys.sunset
                    val sealevel = responsebody.main.sea_level
                    val condition = responsebody.weather.firstOrNull()?.main?:"unknown"
                    val maxtemp = responsebody.main.temp_max
                    val mintemp = responsebody.main.temp_min

                    val sunriseTime = responsebody.sys.sunrise
                    val sunsetTime = responsebody.sys.sunset

                    // Convert Unix timestamps to Date objects
                    val sunriseDate =
                        Date(sunriseTime * 1000L) // Multiply by 1000 to convert seconds to milliseconds
                    val sunsetDate = Date(sunsetTime * 1000L)

// Format Date objects to a readable time format
                    val dateFormat =
                        SimpleDateFormat("hh:mm a", Locale.getDefault()) // Example: "10:30 AM"
                    val formattedSunrise = dateFormat.format(sunriseDate)
                    val formattedSunset = dateFormat.format(sunsetDate)

                    binding.sunrise.text = formattedSunrise
                    binding.sunset.text = formattedSunset


                    binding.temp.text = "$temperature °C"
                    binding.humidity.text = "$humidity  %"
                    binding.weather.text = condition
                    binding.maxtemp.text = "H $maxtemp °C"
                    binding.mintemp.text = "L $mintemp °C"
                    binding.wind.text = "$wind m/s"
                    binding.seaLevel.text = "$sealevel hPa"


                    binding.day.text = dayName(System.currentTimeMillis())
                    binding.date.text = date()
                    binding.today1.text = "$cityName"



                    changeImagesAccordingToWeatherCondition(condition)





                }
            }

            override fun onFailure(call: Call<WeatherApp>, p1: Throwable) {

            }

        })

    }

    private fun changeImagesAccordingToWeatherCondition(conditions: String) {
        when (conditions) {
            "Clear" -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieanimation.setAnimation(R.raw.sun)
                setStatusBarColor(R.color.sunny_status_bar)



            }
            "Clouds", "Mist", "Fog", "Haze" -> {
                binding.lottieanimation.setAnimation(R.raw.cloud)
                binding.root.setBackgroundResource(R.drawable.colud_background)
                setStatusBarColor(R.color.cloud_status_bar)

            }
            "Rain" -> {
                binding.lottieanimation.setAnimation(R.raw.rain)
                binding.root.setBackgroundResource(R.drawable.rainy_bg)
                setStatusBarColor(R.color.rain_status_bar)
//
            }
            "Snow" -> {
                binding.lottieanimation.setAnimation(R.raw.snow)
                binding.root.setBackgroundResource(R.drawable.snow_bg)
                setStatusBarColor(R.color.snow_status_bar)

//
            }
            else -> {
                binding.lottieanimation.setAnimation(R.raw.sun)
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                setStatusBarColor(R.color.sunny_status_bar)

//
            }
        }
        binding.lottieanimation.playAnimation()
    }

    private fun date(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy",Locale.getDefault())
        return sdf.format((Date()))
    }

    private fun dayName(timestamp: Long): String {
        val sdf = SimpleDateFormat("EEEE",Locale.getDefault())
        return sdf.format((Date()))
    }


}