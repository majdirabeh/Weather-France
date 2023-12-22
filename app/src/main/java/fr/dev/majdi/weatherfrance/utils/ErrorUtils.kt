package fr.dev.majdi.weatherfrance.utils

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import fr.dev.majdi.weatherfrance.domain.model.Error
import retrofit2.Response

/**
 * Created by Majdi RABEH on 15/11/2023.
 * Email m.rabeh.majdi@gmail.com
 */

object ErrorUtils {
    fun parseError(response: Response<*>): Error? {
        return try {
            response.errorBody()?.let {
                val convertedJson = convertUsingJsonParser(it.string())
                Error(response.code().toString(), convertedJson?.get("message").toString().replace("\"", ""))
            } ?: Error(response.code().toString(), "Something went wrong, please try again!")
        } catch (e: JsonParseException) {
            Error(response.code().toString(), e.message.toString())
        }
    }
}

private fun convertUsingJsonParser(jsonString: String): JsonObject? {
    return JsonParser().parse(jsonString).asJsonObject
}