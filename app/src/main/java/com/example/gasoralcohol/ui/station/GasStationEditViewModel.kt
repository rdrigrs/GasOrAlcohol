package com.example.gasoralcohol.ui.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gasoralcohol.data.GasStationRepository
import com.example.gasoralcohol.model.GasStation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class GasStationEditUiState(
    val id: Long? = null,
    val name: String = "",
    val address: String = "",
    val gasPrice: String = "",
    val alcoholPrice: String = "",
    val latitude: String = "",
    val longitude: String = ""
)

class GasStationEditViewModel(stationId: Long, private val repository: GasStationRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(GasStationEditUiState())
    val uiState: StateFlow<GasStationEditUiState> = _uiState.asStateFlow()

    init {
        if (stationId != -1L) {
            val station = repository.getStationById(stationId)
            station?.let {
                _uiState.value = GasStationEditUiState(
                    id = it.id,
                    name = it.name,
                    address = it.address,
                    gasPrice = it.gasPrice.toString(),
                    alcoholPrice = it.alcoholPrice.toString(),
                    latitude = if (it.latitude != 0.0) it.latitude.toString() else "",
                    longitude = if (it.longitude != 0.0) it.longitude.toString() else ""
                )
            }
        }
    }

    fun preFillPrices(gasPrice: Double, alcoholPrice: Double) {
        if (_uiState.value.id == null) { // Only pre-fill for new stations
            _uiState.update {
                it.copy(
                    gasPrice = if (gasPrice > 0.0) gasPrice.toString() else it.gasPrice,
                    alcoholPrice = if (alcoholPrice > 0.0) alcoholPrice.toString() else it.alcoholPrice
                )
            }
        }
    }

    fun onNameChanged(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onAddressChanged(address: String) {
        _uiState.update { it.copy(address = address) }
    }

    fun onGasPriceChanged(price: String) {
        _uiState.update { it.copy(gasPrice = price) }
    }

    fun onAlcoholPriceChanged(price: String) {
        _uiState.update { it.copy(alcoholPrice = price) }
    }

    fun onLatitudeChanged(latitude: String) {
        _uiState.update { it.copy(latitude = latitude) }
    }

    fun onLongitudeChanged(longitude: String) {
        _uiState.update { it.copy(longitude = longitude) }
    }

    fun saveStation() {
        val currentState = _uiState.value
        val newStation = GasStation(
            id = currentState.id ?: Date().time,
            name = currentState.name,
            address = currentState.address,
            gasPrice = currentState.gasPrice.toDoubleOrNull() ?: 0.0,
            alcoholPrice = currentState.alcoholPrice.toDoubleOrNull() ?: 0.0,
            date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
            latitude = currentState.latitude.toDoubleOrNull() ?: 0.0,
            longitude = currentState.longitude.toDoubleOrNull() ?: 0.0
        )
        repository.saveStation(newStation)
    }

    fun deleteStation() {
        _uiState.value.id?.let { repository.deleteStation(it) }
    }
}

class GasStationEditViewModelFactory(
    private val stationId: Long,
    private val repository: GasStationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GasStationEditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GasStationEditViewModel(stationId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}