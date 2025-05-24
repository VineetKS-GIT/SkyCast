package com.example.weatherapplication

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater

class CityAdapter(private val cities: List<String>, private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_city, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.bind(city)
    }

    override fun getItemCount(): Int = cities.size

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cityNameTextView: TextView = itemView.findViewById(R.id.cityNameTextView)

        init {
            itemView.setOnClickListener {
                onItemClick(cities[adapterPosition])
            }
        }

        fun bind(city: String) {
            cityNameTextView.text = city
        }
    }
}
