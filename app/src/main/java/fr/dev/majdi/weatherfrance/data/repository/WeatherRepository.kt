package fr.dev.majdi.weatherfrance.data.repository

import fr.dev.majdi.weatherfrance.data.remote.RemoteDataSource
import fr.dev.majdi.weatherfrance.model.Result
import fr.dev.majdi.weatherfrance.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Majdi RABEH on 15/11/2023.
 * Email m.rabeh.majdi@gmail.com
 */
class WeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
    // we can add local data source her to get data from database if need
) {

    fun fetchWeatherData(city: String, apiKey: String, currentLanguage: String): Flow<Result<WeatherResponse>> = flow {
        emit(Result.loading())
        emit(remoteDataSource.getWeatherByCity(city, apiKey, currentLanguage))
    }

}