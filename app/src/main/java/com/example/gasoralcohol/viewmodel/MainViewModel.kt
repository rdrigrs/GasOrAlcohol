package com.example.gasoralcohol.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gasoralcohol.R
import com.example.gasoralcohol.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MainUiState(
    val gasAutonomy: String = "",
    val alcoholAutonomy: String = "",
    val gasPrice: String = "",
    val alcoholPrice: String = "",
    val result: String = ""
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val settingsRepository = SettingsRepository(application)

    init {
        loadAutonomy()
    }

    private fun loadAutonomy() {
        val gasAutonomy = settingsRepository.getGasAutonomy()
        val alcoholAutonomy = settingsRepository.getAlcoholAutonomy()

        _uiState.update {
            it.copy(
                gasAutonomy = if (gasAutonomy > 0) gasAutonomy.toString() else "",
                alcoholAutonomy = if (alcoholAutonomy > 0) alcoholAutonomy.toString() else ""
            )
        }
    }

    fun onGasAutonomyChanged(gasAutonomy: String) {
        _uiState.update { it.copy(gasAutonomy = gasAutonomy) }
        gasAutonomy.toDoubleOrNull()?.let { settingsRepository.saveGasAutonomy(it) }
    }

    fun onAlcoholAutonomyChanged(alcoholAutonomy: String) {
        _uiState.update { it.copy(alcoholAutonomy = alcoholAutonomy) }
        alcoholAutonomy.toDoubleOrNull()?.let { settingsRepository.saveAlcoholAutonomy(it) }
    }

    fun onGasPriceChanged(gasPrice: String) {
        _uiState.update { it.copy(gasPrice = gasPrice) }
    }

    fun onAlcoholPriceChanged(alcoholPrice: String) {
        _uiState.update { it.copy(alcoholPrice = alcoholPrice) }
    }

    fun calculate() {
        val gasAutonomy = _uiState.value.gasAutonomy.toDoubleOrNull() ?: 0.0
        val alcoholAutonomy = _uiState.value.alcoholAutonomy.toDoubleOrNull() ?: 0.0
        val gasPrice = _uiState.value.gasPrice.toDoubleOrNull() ?: 0.0
        val alcoholPrice = _uiState.value.alcoholPrice.toDoubleOrNull() ?: 0.0

        if (gasAutonomy == 0.0 || alcoholAutonomy == 0.0 || gasPrice == 0.0 || alcoholPrice == 0.0) {
            _uiState.update { it.copy(result = getApplication<Application>().getString(R.string.fill_all_fields)) }
            return
        }

        val gasResult = gasAutonomy / gasPrice
        val alcoholResult = alcoholAutonomy / alcoholPrice

        val result = if (gasResult > alcoholResult) {
            getApplication<Application>().getString(R.string.gas_is_better)
        } else if (alcoholResult > gasResult) {
            getApplication<Application>().getString(R.string.alcohol_is_better)
        } else {
            getApplication<Application>().getString(R.string.both_are_equivalent)
        }

        _uiState.update { it.copy(result = result) }
    }

    fun hasValidPrices(): Boolean {
        val gasPrice = _uiState.value.gasPrice.toDoubleOrNull() ?: 0.0
        val alcoholPrice = _uiState.value.alcoholPrice.toDoubleOrNull() ?: 0.0
        return gasPrice > 0.0 && alcoholPrice > 0.0
    }
}