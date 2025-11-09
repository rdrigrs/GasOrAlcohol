package com.example.gasoralcohol.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gasoralcohol.MainScreen
import com.example.gasoralcohol.R
import com.example.gasoralcohol.data.GasStationRepository
import com.example.gasoralcohol.ui.station.GasStationEditScreen
import com.example.gasoralcohol.ui.station.GasStationEditViewModelFactory
import com.example.gasoralcohol.ui.station.GasStationListScreen
import com.example.gasoralcohol.ui.station.GasStationListViewModel
import com.example.gasoralcohol.ui.station.GasStationListViewModelFactory
import com.example.gasoralcohol.viewmodel.MainViewModel

@Composable
fun AppNavigation(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val gasStationRepository = GasStationRepository(context)

    val gasStationListViewModel: GasStationListViewModel = viewModel(
        factory = GasStationListViewModelFactory(gasStationRepository)
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Show bottom navigation only on main screens, not on edit screen
    val showBottomBar = currentRoute in listOf("main", "gas_station_list")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "main",
                        onClick = {
                            if (currentRoute != "main") {
                                navController.navigate("main") {
                                    popUpTo("main") { inclusive = false }
                                }
                            }
                        },
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text(stringResource(R.string.nav_calculator)) }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "gas_station_list",
                        onClick = {
                            if (currentRoute != "gas_station_list") {
                                navController.navigate("gas_station_list") {
                                    popUpTo("main") { inclusive = false }
                                }
                            }
                        },
                        icon = { Icon(Icons.Filled.List, contentDescription = null) },
                        label = { Text(stringResource(R.string.nav_stations)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") {
                MainScreen(
                    mainViewModel = mainViewModel,
                    onNavigateToGasStationList = {
                        navController.navigate("gas_station_list")
                    },
                    onNavigateToAddStation = { gasPrice, alcoholPrice ->
                        navController.navigate("gas_station_edit/-1/$gasPrice/$alcoholPrice")
                    }
                )
            }
            composable("gas_station_list") {
                GasStationListScreen(
                    viewModel = gasStationListViewModel,
                    onNavigateToDetail = { stationId ->
                        navController.navigate("gas_station_edit/$stationId/0.0/0.0")
                    },
                    onNavigateToAdd = {
                        navController.navigate("gas_station_edit/-1/0.0/0.0")
                    }
                )
            }
            composable(
                route = "gas_station_edit/{stationId}/{gasPrice}/{alcoholPrice}",
                arguments = listOf(
                    navArgument("stationId") { type = NavType.LongType },
                    navArgument("gasPrice") { type = NavType.FloatType },
                    navArgument("alcoholPrice") { type = NavType.FloatType }
                )
            ) {
                val stationId = it.arguments?.getLong("stationId") ?: -1L
                val gasPrice = it.arguments?.getFloat("gasPrice")?.toDouble() ?: 0.0
                val alcoholPrice = it.arguments?.getFloat("alcoholPrice")?.toDouble() ?: 0.0
                GasStationEditScreen(
                    viewModel = viewModel(
                        factory = GasStationEditViewModelFactory(stationId, gasStationRepository)
                    ),
                    preFillGasPrice = gasPrice,
                    preFillAlcoholPrice = alcoholPrice,
                    onSave = {
                        gasStationListViewModel.loadStations()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}