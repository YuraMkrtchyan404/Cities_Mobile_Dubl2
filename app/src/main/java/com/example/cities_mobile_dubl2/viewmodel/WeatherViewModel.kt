package com.example.cities_mobile_dubl2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cities_mobile_dubl2.constants.API_KEY
import com.example.cities_mobile_dubl2.data.cities
import com.example.cities_mobile_dubl2.model.WeatherData
import com.example.cities_mobile_dubl2.network.RetrofitClient
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherService = RetrofitClient.weatherService

    private val _weatherData = MutableLiveData<List<WeatherData>>()
    val weatherData: LiveData<List<WeatherData>> get() = _weatherData

    init {
        viewModelScope.launch {
            try {
                // Assuming you have a function in com.example.cities_mobile_dubl2.WeatherService to get weather data for each city
                val weatherList = mutableListOf<WeatherData>()
                for (city in cities) {
                    val response = weatherService.getWeather(city.name, API_KEY, "en")
                    response?.let {
                        weatherList.add(it)
                    }
                }
                _weatherData.value = weatherList
            } catch (e: Exception) {
                Log.e("Retrofit", "Error fetching weather data", e)
            }
        }
    }
}
