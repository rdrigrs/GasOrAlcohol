package com.example.gasoralcohol.ui.station

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gasoralcohol.R

@Composable
fun GasStationEditScreen(
    modifier: Modifier = Modifier,
    viewModel: GasStationEditViewModel,
    preFillGasPrice: Double = 0.0,
    preFillAlcoholPrice: Double = 0.0,
    onSave: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isEditing = uiState.id != null

    // Pre-fill prices if provided
    androidx.compose.runtime.LaunchedEffect(Unit) {
        if (preFillGasPrice > 0.0 || preFillAlcoholPrice > 0.0) {
            viewModel.preFillPrices(preFillGasPrice, preFillAlcoholPrice)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            value = uiState.name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = { Text(stringResource(R.string.gas_station_name)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = uiState.address,
            onValueChange = { viewModel.onAddressChanged(it) },
            label = { Text(stringResource(R.string.address)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = uiState.gasPrice,
            onValueChange = { viewModel.onGasPriceChanged(it) },
            label = { Text(stringResource(R.string.gas_price)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = uiState.alcoholPrice,
            onValueChange = { viewModel.onAlcoholPriceChanged(it) },
            label = { Text(stringResource(R.string.alcohol_price)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = uiState.latitude,
            onValueChange = { viewModel.onLatitudeChanged(it) },
            label = { Text(stringResource(R.string.latitude)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = uiState.longitude,
            onValueChange = { viewModel.onLongitudeChanged(it) },
            label = { Text(stringResource(R.string.longitude)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    viewModel.saveStation()
                    onSave()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.save))
            }
            if (isEditing) {
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        viewModel.deleteStation()
                        onSave()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.delete))
                }
            }
        }
    }
}