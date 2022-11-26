package com.example.airports

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.airports.databinding.AirportInfoBinding

class AirportAdapter: RecyclerView.Adapter<AirportAdapter.AirportViewHolder>() {
    private val airports = mutableListOf<Airport>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateAirportsStatus(updatedAirports: List<Airport>){
        airports.apply {
            clear()
            addAll(updatedAirports)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportViewHolder {
        val binding = AirportInfoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AirportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AirportViewHolder, position: Int) {
        if (position > 0) holder.bind(airports[position - 1])

    }

    override fun getItemCount(): Int = airports.size + 1

    inner class AirportViewHolder (private val itemBinding: AirportInfoBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(airport: Airport) {
            val (code, name, delay, weather) = airport
            val clock = if (delay) "\uD83D\uDD52" else ""

            itemView.apply {
                itemBinding.airportCode.text = code
                itemBinding.airportName.text = name
                itemBinding.airportTemperature.text = weather.temperature.firstOrNull()
                itemBinding.airportDelay.text = clock
            }
        }
    }
}
