package com.example.gasoralcohol.data

import android.content.Context

private const val PREFS_NAME = "settings_prefs"
private const val KEY_GAS_AUTONOMY = "gas_autonomy"
private const val KEY_ALCOHOL_AUTONOMY = "alcohol_autonomy"

class SettingsRepository(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getGasAutonomy(): Double {
        return prefs.getString(KEY_GAS_AUTONOMY, "")?.toDoubleOrNull() ?: 0.0
    }

    fun getAlcoholAutonomy(): Double {
        return prefs.getString(KEY_ALCOHOL_AUTONOMY, "")?.toDoubleOrNull() ?: 0.0
    }

    fun saveGasAutonomy(autonomy: Double) {
        prefs.edit().putString(KEY_GAS_AUTONOMY, autonomy.toString()).apply()
    }

    fun saveAlcoholAutonomy(autonomy: Double) {
        prefs.edit().putString(KEY_ALCOHOL_AUTONOMY, autonomy.toString()).apply()
    }

    fun saveAutonomy(gasAutonomy: Double, alcoholAutonomy: Double) {
        prefs.edit()
            .putString(KEY_GAS_AUTONOMY, gasAutonomy.toString())
            .putString(KEY_ALCOHOL_AUTONOMY, alcoholAutonomy.toString())
            .apply()
    }
}