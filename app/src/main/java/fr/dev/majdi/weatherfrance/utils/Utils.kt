package fr.dev.majdi.weatherfrance.utils

import fr.dev.majdi.weatherfrance.R

/**
 * Created by Majdi RABEH on 15/11/2023.
 * Email m.rabeh.majdi@gmail.com
 */

object Utils {

    // Helper function to map OpenWeatherMap icon codes to local resources
    fun getWeatherIcon(iconId: Int): Int {
        return when (iconId) {
            500, 501, 502, 503, 504 -> R.drawable.rain
            800 -> R.drawable.wb_sunny
            803, 804 -> R.drawable.wb_cloudy
            else -> {
                R.drawable.wb_cloudy
            }
        }
    }
}
