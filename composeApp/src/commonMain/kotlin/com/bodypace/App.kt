// App.kt — body-pace entry point
package com.bodypace

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import com.bodypace.ui.screens.HomeScreen
import com.bodypace.model.RunLog

@Composable
fun App() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        // TODO: load from persistence / weather API
        val weather = remember {
            com.bodypace.model.WeatherSnapshot(
                tempCelsius = 18, condition = "晴", humidity = 55,
                windKmh = 8, gearSuggestion = "短袖+短裤，体感舒适"
            )
        }
        val logs = remember { mutableStateListOf<RunLog>() }

        HomeScreen(
            weather = weather,
            logs = logs,
            runCount = logs.size,
            totalKm = logs.sumOf { it.distanceKm },
            lastRun = logs.lastOrNull(),
            onStartRun = { /* navigate to run screen */ }
        )
    }
}
