package com.example.gasoralcohol.viewmodel

import androidx.lifecycle.ViewModel
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

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun onGasAutonomyChanged(gasAutonomy: String) {
        _uiState.update { it.copy(gasAutonomy = gasAutonomy) }
    }

    fun onAlcoholAutonomyChanged(alcoholAutonomy: String) {
        _uiState.update { it.copy(alcoholAutonomy = alcoholAutonomy) }
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
            _uiState.update { it.copy(result = "Please, fill all the fields.") }
            return
        }

        val gasResult = gasAutonomy / gasPrice
        val alcoholResult = alcoholAutonomy / alcoholPrice

        val result = if (gasResult > alcoholResult) {
            "Gas is better"
        } else if (alcoholResult > gasResult) {
            "Alcohol is better"
        } else {
            "Both are equivalent"
        }

        _uiState.update { it.copy(result = result) }
    }
}
