// ForecastAdapter.kt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.ForecastViewHolder
import com.example.weatherapplication.ForecastWeatherApi
import com.example.weatherapplication.R

class ForecastAdapter(private val forecastList: List<ForecastWeatherApi.data>) :
    RecyclerView.Adapter<ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewlayout, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecastData = forecastList[position]
        holder.forecastHumidity.text = "${forecastData.main?.humidity}%"
        holder.mintempTextView.text = "${forecastData.main?.tempMin} °C"
        holder.maxTempTextView.text = "${forecastData.main?.tempMax} °C"


        // Set other weather details to TextViews in ViewHolder
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}
