package fr.dev.majdi.weatherfrance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.dev.majdi.weatherfrance.data.repository.WeatherRepository
import fr.dev.majdi.weatherfrance.domain.model.Cities
import fr.dev.majdi.weatherfrance.domain.model.WeatherResponse
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
    private var callApiRennes = false // for calling api one time
    private var callApiParis = false // for calling api one time
    private var callApiNantes = false // for calling api one time
    private var callApiBordeaux = false // for calling api one time
    private var callApiLyon = false // for calling api one time
    fun fetchWeatherData(elapsedTime: Int, apiKey: String, currentLanguage: String) {
        when (elapsedTime) {
            in 0 until 10000 -> {
                if (!callApiRennes) {
                    fetchWeather(Cities.Rennes.name, apiKey, currentLanguage)
                    callApiRennes = true
                }
            }

            in 10000 until 20000 -> {
                if (!callApiParis) {
                    fetchWeather(Cities.Paris.name, apiKey, currentLanguage)
                    callApiParis = true
                }
            }

            in 20000 until 30000 -> {
                if (!callApiNantes) {
                    fetchWeather(Cities.Nantes.name, apiKey, currentLanguage)
                    callApiNantes = true
                }
            }

            in 30000 until 40000 -> {
                if (!callApiBordeaux) {
                    fetchWeather(Cities.Bordeaux.name, apiKey, currentLanguage)
                    callApiBordeaux = true
                }
            }

            in 40000 until 50000 -> {
                if (!callApiLyon) {
                    fetchWeather(Cities.Lyon.name, apiKey, currentLanguage)
                    callApiLyon = true
                }
            }

            in 50000 until 60000 -> {
                callApiRennes = false
                callApiParis = false
                callApiNantes = false
                callApiBordeaux = false
                callApiLyon = false
            }

            else -> {
                // Handle cases outside the specified intervals
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