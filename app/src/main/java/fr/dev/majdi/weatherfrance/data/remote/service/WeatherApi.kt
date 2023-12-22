package fr.dev.majdi.weatherfrance.data.remote.service

import fr.dev.majdi.weatherfrance.domain.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by Majdi RABEH on 15/11/2023.
 * Email m.rabeh.majdi@gmail.com
 */
interface WeatherApi {

    @Headers("Content-Type: application/json")
    @GET("/data/2.5/weather")
    suspend fun getWeatherData(
        @Query("q") city: String,
        @Query("appid") id: String,
        @Query("lang") lang: String
    ): Response<WeatherResponse>

}