package com.example.cities_mobile_dubl2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cities_mobile_dubl2.ui.CitiesScreen
import com.example.cities_mobile_dubl2.ui.WelcomeScreen
import com.example.cities_mobile_dubl2.viewmodel.WeatherViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cities_mobile_dubl2.constants.WELCOME_SCREEN_ROUTE
import com.example.cities_mobile_dubl2.constants.SECOND_SCREEN_ROUTE


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            AppUI(navController = navController, viewModel = WeatherViewModel())
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppUI(navController: NavHostController, viewModel: WeatherViewModel) {
        val weatherList = viewModel.weatherData.value
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route ?: ""

        BackHandler(onBack = {
            if (currentRoute == SECOND_SCREEN_ROUTE) {
                navController.navigate(WELCOME_SCREEN_ROUTE) {
                    popUpTo(WELCOME_SCREEN_ROUTE) {
                        inclusive = true
                    }
                }
            }
        })

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Cities Weather") }
                )
            },
            content = {
                NavHost(navController = navController, startDestination = WELCOME_SCREEN_ROUTE) {
                    composable(WELCOME_SCREEN_ROUTE) {
                        WelcomeScreen(navController = navController)
                    }
                    composable(SECOND_SCREEN_ROUTE) {
                        CitiesScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        )
    }
}
