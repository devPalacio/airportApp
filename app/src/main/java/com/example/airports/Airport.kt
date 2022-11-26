package com.example.airports

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
class Weather(@SerialName("Temp") val temperature: Array<String>)

@Serializable
data class Airport(
    @SerialName("IATA") val code: String,
    @SerialName("Name") val name: String,
    @SerialName("Delay") val delay: Boolean,
    @SerialName("Weather") val weather: Weather = Weather(arrayOf(""))
) {
    companion object {
        private val format = Json {ignoreUnknownKeys = true}
        fun sort(airports: List<Airport>): List<Airport> {
            return airports.sortedBy { airport: Airport -> airport.name }
        }

        fun getAirportData(code: String) =
            try {
                format.decodeFromString<Airport>(fetchData(code))
            } catch (ex: java.lang.Exception) {
                Airport(code, "Invalid Airport", false)
            }

        private fun fetchData(code: String): String =
            java.net.URL("https://soa.smext.faa.gov/asws/api/airport/status/$code").readText()
    }
}