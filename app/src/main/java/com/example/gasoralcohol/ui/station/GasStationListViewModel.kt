package com.example.gasoralcohol.ui.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gasoralcohol.data.GasStationRepository
import com.example.gasoralcohol.model.GasStation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GasStationListUiState(
    val stations: List<GasStation> = emptyList()
)

class GasStationListViewModel(private val repository: GasStationRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(GasStationListUiState())
    val uiState: StateFlow<GasStationListUiState> = _uiState.asStateFlow()

    init {
        loadStations()
    }

    fun loadStations() {
        _uiState.value = GasStationListUiState(stations = repository.getStations())
    }
}

class GasStationListViewModelFactory(private val repository: GasStationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GasStationListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GasStationListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}