package com.example.gasoralcohol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gasoralcohol.ui.AppNavigation
import com.example.gasoralcohol.ui.theme.GasOrAlcoholTheme
import com.example.gasoralcohol.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GasOrAlcoholTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(mainViewModel = mainViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    onNavigateToGasStationList: () -> Unit,
    onNavigateToAddStation: (Double, Double) -> Unit
) {
    val uiState by mainViewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
            .padding(PaddingValues(horizontal = 16.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            modifier = Modifier.padding(10.dp),
            value = uiState.gasAutonomy,
            onValueChange = { mainViewModel.onGasAutonomyChanged(it) },
            label = { Text(stringResource(R.string.gas_autonomy)) }
        )
        TextField(
            modifier = Modifier.padding(10.dp),
            value = uiState.alcoholAutonomy,
            onValueChange = { mainViewModel.onAlcoholAutonomyChanged(it) },
            label = { Text(stringResource(R.string.alcohol_autonomy)) }
        )
        TextField(
            modifier = Modifier.padding(10.dp),
            value = uiState.gasPrice,
            onValueChange = { mainViewModel.onGasPriceChanged(it) },
            label = { Text(stringResource(R.string.gas_price)) }
        )
        TextField(
            modifier = Modifier.padding(10.dp),
            value = uiState.alcoholPrice,
            onValueChange = { mainViewModel.onAlcoholPriceChanged(it) },
            label = { Text(stringResource(R.string.alcohol_price)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { mainViewModel.calculate() },
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(stringResource(R.string.calculate))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = uiState.result)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val gasPrice = uiState.gasPrice.toDoubleOrNull() ?: 0.0
                val alcoholPrice = uiState.alcoholPrice.toDoubleOrNull() ?: 0.0
                onNavigateToAddStation(gasPrice, alcoholPrice)
            },
            enabled = mainViewModel.hasValidPrices(),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(stringResource(R.string.save_to_list))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    GasOrAlcoholTheme {
        MainScreen(
            mainViewModel = viewModel(),
            onNavigateToGasStationList = {},
            onNavigateToAddStation = { _, _ -> }
        )
    }
}
