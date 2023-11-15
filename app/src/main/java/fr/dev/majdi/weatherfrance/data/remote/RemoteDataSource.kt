package fr.dev.majdi.weatherfrance.data.remote

import fr.dev.majdi.weatherfrance.model.Result
import fr.dev.majdi.weatherfrance.model.WeatherResponse
import fr.dev.majdi.weatherfrance.service.WeatherApi
import fr.dev.majdi.weatherfrance.service.apiResponse
import javax.inject.Inject

/**
 * Created by Majdi RABEH on 15/11/2023.
 * Email m.rabeh.majdi@gmail.com
 */
class RemoteDataSource @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getWeatherByCity(city: String, apiKey: String, currentLanguage: String): Result<WeatherResponse> {
        return apiResponse(
            request = { weatherApi.getWeatherData(city, apiKey,currentLanguage) },
            defaultErrorMessage = "Error when fetching data!"
        )
    }

}