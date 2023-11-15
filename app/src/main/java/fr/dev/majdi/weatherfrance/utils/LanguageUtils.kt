package fr.dev.majdi.weatherfrance.utils

import android.content.Context
import android.os.Build
import android.os.LocaleList

/**
 * Created by Majdi RABEH on 15/11/2023.
 * Email m.rabeh.majdi@gmail.com
 */
object LanguageUtils {

    fun getCurrentLanguage(context: Context): String {
        val config = context.resources.configuration
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val locales: LocaleList = config.locales
            if (locales.size() > 0) {
                locales.get(0).language
            } else {
                ""
            }
        } else {
            // Fallback for older devices
            config.locale.language
        }
    }
}