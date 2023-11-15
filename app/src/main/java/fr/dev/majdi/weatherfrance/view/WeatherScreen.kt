package fr.dev.majdi.weatherfrance.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fr.dev.majdi.weatherfrance.R
import fr.dev.majdi.weatherfrance.model.WeatherResponse
import fr.dev.majdi.weatherfrance.utils.Constants
import fr.dev.majdi.weatherfrance.utils.Utils.getWeatherIcon
import fr.dev.majdi.weatherfrance.viewmodel.WeatherViewModel
import kotlinx.coroutines.delay

/**
 * Created by Majdi RABEH on 15/11/2023.
 * Email m.rabeh.majdi@gmail.com
 */
var isProgressNotFinished = mutableStateOf(true)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    navController: NavHostController,
    weatherViewModel: WeatherViewModel,
    currentLanguage: String
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = {
                        isProgressNotFinished.value = true
                        weatherViewModel.responseList.clear()
                        // Navigate back to the first screen
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isProgressNotFinished.value) {
                    weatherViewModel.responseList.clear()
                    WeatherList(weatherDataList = weatherViewModel.responseList)
                }
                ProgressBar(weatherViewModel, currentLanguage)
            }
        }
    )

}


@Composable
fun ProgressBar(weatherViewModel: WeatherViewModel, currentLanguage: String) {
    Column(
        modifier = Modifier
            .padding(bottom = 32.dp) // margin
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        var currentMessageIndex by remember { mutableIntStateOf(0) }
        val messages = listOf(
            stringResource(id = R.string.first_message),
            stringResource(id = R.string.second_message),
            stringResource(id = R.string.third_message)
        )
        var message by remember { mutableStateOf(messages[currentMessageIndex]) }

        var progress by remember { mutableFloatStateOf(0f) }

        // LaunchedEffect for updating the text every 6 seconds
        LaunchedEffect(isProgressNotFinished.value) {
            while (isProgressNotFinished.value) {
                delay(6000)
                currentMessageIndex = (currentMessageIndex + 1) % messages.size
                message = messages[currentMessageIndex]
            }
        }

        // LaunchedEffect for updating the progress bar every second
        LaunchedEffect(isProgressNotFinished.value) {
            var elapsedTime = 0L

            while (isProgressNotFinished.value && elapsedTime < 60000) { // Limit progress to 60 seconds
                delay(1000)
                weatherViewModel.fetchWeatherData(
                    elapsedTime.toInt(),
                    Constants.API_KEY,
                    currentLanguage
                )
                elapsedTime += 1000
                // Update progress by approximately 1.67% every second (100 / 60)
                progress += 1.67f
            }
            // Ensure progress reaches 100% exactly after 60 seconds
            progress = 0f
            isProgressNotFinished.value = false
        }

        if (isProgressNotFinished.value) {
            Text(
                modifier = Modifier
                    .height(50.dp)
                    .width(300.dp),
                text = message,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .height(30.dp)
                    .background(Color.Gray)
                    .width(300.dp)
            ) {
                // in this box we are creating one more box.
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .height(30.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF0F9D58),
                                    Color(0xF055CA4D)
                                )
                            )
                        )
                        .width(300.dp * progress / 100)
                )
                // on below line we are creating a text for our box
                Text(
                    text = "${(progress).toInt()} %",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .width(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        //Start the progress again and reset all values
                        isProgressNotFinished.value = true
                        weatherViewModel.responseList.clear()
                    },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = stringResource(id = R.string.weather_button_text))
                }
            }
        }
    }
}

@Composable
fun WeatherList(weatherDataList: List<WeatherResponse>) {
    if (weatherDataList.isEmpty()) {
        Text(
            modifier = Modifier.padding(top = 100.dp),
            text = stringResource(id = R.string.data_not_found),
            fontWeight = FontWeight.Bold
        )
    } else {
        LazyColumn {
            items(weatherDataList) { weatherResponse ->
                WeatherItem(weatherResponse)
            }
        }
    }
}

@Composable
fun WeatherItem(weatherResponse: WeatherResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon representing weather conditions I use just 3 icons we can add more
        val weatherIcon = when {
            weatherResponse.weather.isNotEmpty() -> {
                val iconId = weatherResponse.weather[0].id
                getWeatherIcon(iconId)
            }

            else -> R.drawable.wb_cloudy
        }

        Image(
            painter = painterResource(id = weatherIcon),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
        )

        // Display weather information
        Column {
            Text(
                text = stringResource(id = R.string.item_city) + " " + weatherResponse.name,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.item_temperature) + " " + weatherResponse.main.temp + " " + "°C",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = (stringResource(id = R.string.item_cloud) + " " + weatherResponse.weather.getOrNull(
                    0
                )?.description) ?: "Unknown",
                fontWeight = FontWeight.Bold
            )
        }
    }
}



