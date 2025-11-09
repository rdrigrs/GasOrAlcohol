package com.example.gasoralcohol.data

import android.content.Context
import com.example.gasoralcohol.model.GasStation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val PREFS_NAME = "gas_station_prefs"
private const val KEY_GAS_STATIONS = "gas_stations"

class GasStationRepository(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getStations(): List<GasStation> {
        val json = prefs.getString(KEY_GAS_STATIONS, null)
        return if (json != null) {
            try {
                val type = object : TypeToken<List<GasStation>>() {}.type
                gson.fromJson(json, type) ?: emptyList()
            } catch (t: Throwable) {
                // Data is likely corrupt or in an old format. Clear it and return an empty list.
                saveStations(emptyList())
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    fun getStationById(id: Long): GasStation? {
        return getStations().find { it.id == id }
    }

    fun saveStation(station: GasStation) {
        val stations = getStations().toMutableList()
        val index = stations.indexOfFirst { it.id == station.id }
        if (index != -1) {
            stations[index] = station
        } else {
            stations.add(station)
        }
        saveStations(stations)
    }

    fun deleteStation(id: Long) {
        val stations = getStations().toMutableList()
        stations.removeAll { it.id == id }
        saveStations(stations)
    }

    private fun saveStations(stations: List<GasStation>) {
        val json = gson.toJson(stations)
        prefs.edit().putString(KEY_GAS_STATIONS, json).apply()
    }
}