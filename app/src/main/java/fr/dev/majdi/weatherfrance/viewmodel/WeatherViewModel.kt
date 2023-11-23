package fr.dev.majdi.weatherfrance.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.dev.majdi.weatherfrance.data.repository.WeatherRepository
import fr.dev.majdi.weatherfrance.model.Cities
import fr.dev.majdi.weatherfrance.model.WeatherResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Majdi RABEH on 15/11/2023.
 * Email m.rabeh.majdi@gmail.com
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val responseList = mutableListOf<WeatherResponse>()

    fun fetchWeatherData(elapsedTime: Int, apiKey: String, currentLanguage: String) {
        var isAllFetchFinished = false

        when (elapsedTime) {
            0 -> {
                fetchWeather(Cities.Rennes.name, apiKey, currentLanguage)
            }

            10000 -> {
                fetchWeather(Cities.Paris.name, apiKey, currentLanguage)
            }

            20000 -> {
                fetchWeather(Cities.Nantes.name, apiKey, currentLanguage)
            }

            30000 -> {
                fetchWeather(Cities.Bordeaux.name, apiKey, currentLanguage)
            }

            40000 -> {
                fetchWeather(Cities.Lyon.name, apiKey, currentLanguage)
            }

            50000 -> {
                isAllFetchFinished = true
            }
        }

        if (isAllFetchFinished) {
            for (value in responseList) {
                Log.e("LIST", value.toString())
            }
        }
    }


    private fun fetchWeather(city: String, apiKey: String, currentLanguage: String) {
        viewModelScope.launch {
            weatherRepository.fetchWeatherData(city, apiKey, currentLanguage).collect {
                it.data?.let { weatherResponse ->
                    responseList.add(weatherResponse)
                }
            }
        }
    }

}