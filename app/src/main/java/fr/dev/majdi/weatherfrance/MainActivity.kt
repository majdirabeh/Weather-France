package fr.dev.majdi.weatherfrance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.dev.majdi.weatherfrance.model.Screen
import fr.dev.majdi.weatherfrance.ui.theme.WeatherFranceTheme
import fr.dev.majdi.weatherfrance.utils.LanguageUtils
import fr.dev.majdi.weatherfrance.view.WeatherScreen
import fr.dev.majdi.weatherfrance.view.WelcomeScreen
import fr.dev.majdi.weatherfrance.viewmodel.WeatherViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherFranceTheme {
                val navController: NavHostController = rememberNavController()
                val currentLanguage = LanguageUtils.getCurrentLanguage(this)
                // Set up the NavHost with your destinations
                NavHost(
                    navController = navController,
                    startDestination = Screen.WelcomeScreen.name
                ) {
                    composable(Screen.WelcomeScreen.name) { WelcomeScreen(navController) }
                    composable(Screen.WeatherScreen.name) {
                        WeatherScreen(
                            navController,
                            weatherViewModel,
                            currentLanguage
                        )
                    }
                }
            }

        }
    }
}
