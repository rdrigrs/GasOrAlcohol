package com.example.gasoralcohol.ui.station

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gasoralcohol.R
import com.example.gasoralcohol.model.GasStation

@Composable
fun GasStationListScreen(
    modifier: Modifier = Modifier,
    viewModel: GasStationListViewModel,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToAdd: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_gas_station))
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(uiState.stations) { station ->
                GasStationItem(
                    station = station,
                    onItemClick = { onNavigateToDetail(station.id) }
                )
            }
        }
    }
}

@Composable
fun GasStationItem(
    station: GasStation,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    val betterChoiceText = when (station.betterChoice) {
        "alcohol" -> stringResource(R.string.choice_alcohol)
        "gas" -> stringResource(R.string.choice_gas)
        "equivalent" -> stringResource(R.string.choice_equivalent)
        else -> stringResource(R.string.choice_unknown)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = station.name, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            Text(text = station.address, style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
            Text(text = stringResource(R.string.gas_price_label, station.gasPrice))
            Text(text = stringResource(R.string.alcohol_price_label, station.alcoholPrice))
            Text(
                text = stringResource(R.string.best_choice, betterChoiceText),
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = when (station.betterChoice) {
                    "alcohol" -> androidx.compose.material3.MaterialTheme.colorScheme.tertiary
                    "gas" -> androidx.compose.material3.MaterialTheme.colorScheme.primary
                    else -> androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                }
            )
            Text(text = stringResource(R.string.updated_label, station.date), style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
        }
    }
}