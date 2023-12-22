package fr.dev.majdi.weatherfrance.domain.model

/**
 * Created by Majdi RABEH on 15/11/2023.
 * Email m.rabeh.majdi@gmail.com
 */
data class Error(
    val cod: String,
    val message: String? = null
)