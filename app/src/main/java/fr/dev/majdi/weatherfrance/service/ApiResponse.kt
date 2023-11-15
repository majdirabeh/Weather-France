package fr.dev.majdi.weatherfrance.service

import fr.dev.majdi.weatherfrance.model.Result
import fr.dev.majdi.weatherfrance.utils.ErrorUtils
import retrofit2.Response

/**
 * Created by Majdi RABEH on 15/11/2023.
 * Email m.rabeh.majdi@gmail.com
 */
suspend fun <T> apiResponse(
    request: suspend () -> Response<T>,
    defaultErrorMessage: String
): Result<T> {
    return try {
        val result = request.invoke()
        if (result.isSuccessful) {
            return Result.success(result.body())
        } else {
            val errorResponse = ErrorUtils.parseError(result)
            return Result.error(errorResponse?.message ?: defaultErrorMessage, errorResponse)
        }
    } catch (e: Throwable) {
        Result.error("An unknown error occurred. Please try again.", null)
    }
}