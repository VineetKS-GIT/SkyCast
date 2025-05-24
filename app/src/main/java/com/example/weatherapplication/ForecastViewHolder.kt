package com.example.weatherapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mintempTextView: TextView = itemView.findViewById(R.id.forecastMinTemp)
    val maxTempTextView:TextView = itemView.findViewById(R.id.forecastMaxTemp)
    val forecastHumidity:TextView = itemView.findViewById(R.id.ForecastHumidity)
}