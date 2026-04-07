package com.kolo.prayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolo.prayer.data.repository.PreferencesRepository
import com.kolo.prayer.navigation.KoloNavGraph
import com.kolo.prayer.ui.theme.KoloTheme
import com.kolo.prayer.ui.theme.KoloThemeMode
import com.kolo.prayer.ui.theme.Maroon
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Maroon status bar
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Maroon.toArgb()),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ),
        )

        setContent {
            val prefs by preferencesRepository.userPreferences
                .collectAsStateWithLifecycle(
                    initialValue = com.kolo.prayer.data.model.UserPreferences()
                )

            val themeMode = when (prefs.theme) {
                "sepia" -> KoloThemeMode.SEPIA
                "night" -> KoloThemeMode.NIGHT
                else -> KoloThemeMode.DAY
            }

            KoloTheme(
                themeMode = themeMode,
                language = prefs.language,
            ) {
                val startDestination = if (prefs.onboardingDone) {
                    com.kolo.prayer.navigation.Routes.HOME
                } else {
                    com.kolo.prayer.navigation.Routes.ONBOARDING
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    KoloNavGraph(
                        startDestination = startDestination,
                        onSettingsClick = { /* handled inside nav */ },
                    )
                }
            }
        }
    }
}
