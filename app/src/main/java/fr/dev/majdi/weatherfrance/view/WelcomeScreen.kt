package fr.dev.majdi.weatherfrance.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.dev.majdi.weatherfrance.R
import fr.dev.majdi.weatherfrance.model.Screen

/**
 * Created by Majdi RABEH on 15/11/2023.
 * Email m.rabeh.majdi@gmail.com
 */
@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_message),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Button(
            onClick = {
                // Navigate to the next screen
                navController.navigate(Screen.WeatherScreen.name)
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = stringResource(id = R.string.welcome_button_text))
        }
    }
}

