package com.kolo.prayer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ── App Theme Enum ──
enum class KoloThemeMode { DAY, SEPIA, NIGHT }

// ── Composition Local for extended colors that Material3 doesn't cover ──
data class KoloExtraColors(
    val maroon: Color = Maroon,
    val maroonLight: Color = MaroonLight,
    val maroonPale: Color = MaroonPale,
    val gold: Color = Gold,
    val goldLight: Color = GoldLight,
    val goldPale: Color = GoldPale,
    val cream: Color = Cream,
    val ink: Color = Ink,
    val inkMuted: Color = InkMuted,
    val inkFaint: Color = InkFaint,
    val border: Color = Border,
    val borderStrong: Color = BorderStrong,
    val readerBackground: Color = DayBackground,
    val readerOnBackground: Color = DayOnBackground,
)

val LocalKoloColors = staticCompositionLocalOf { KoloExtraColors() }

// ── Material3 Color Schemes ──
private val DayColorScheme = lightColorScheme(
    primary = Maroon,
    onPrimary = Color.White,
    primaryContainer = MaroonPale,
    onPrimaryContainer = Maroon,
    secondary = Gold,
    onSecondary = Color.White,
    secondaryContainer = GoldPale,
    onSecondaryContainer = Gold,
    background = DayBackground,
    onBackground = DayOnBackground,
    surface = DaySurface,
    onSurface = DayOnSurface,
    surfaceVariant = DaySurfaceVariant,
    onSurfaceVariant = DayOnSurfaceVariant,
    outline = Border,
    outlineVariant = BorderStrong,
)

private val SepiaColorScheme = lightColorScheme(
    primary = Maroon,
    onPrimary = Color.White,
    primaryContainer = MaroonPale,
    onPrimaryContainer = Maroon,
    secondary = Gold,
    onSecondary = Color.White,
    secondaryContainer = GoldPale,
    onSecondaryContainer = Gold,
    background = SepiaBackground,
    onBackground = SepiaOnBackground,
    surface = SepiaSurface,
    onSurface = SepiaOnSurface,
    surfaceVariant = SepiaSurfaceVariant,
    onSurfaceVariant = SepiaOnSurfaceVariant,
    outline = Color(0x33705030),
    outlineVariant = Color(0x4D705030),
)

private val NightColorScheme = darkColorScheme(
    primary = NightPrimary,
    onPrimary = Color.White,
    primaryContainer = MaroonDark,
    onPrimaryContainer = NightPrimary,
    secondary = NightSecondary,
    onSecondary = Ink,
    secondaryContainer = Color(0xFF3A3020),
    onSecondaryContainer = GoldLight,
    background = NightBackground,
    onBackground = NightOnBackground,
    surface = NightSurface,
    onSurface = NightOnSurface,
    surfaceVariant = NightSurfaceVariant,
    onSurfaceVariant = NightOnSurfaceVariant,
    outline = Color(0x33AABBAA),
    outlineVariant = Color(0x4DAABBAA),
)

@Composable
fun KoloTheme(
    themeMode: KoloThemeMode = KoloThemeMode.DAY,
    language: String = "ml",
    content: @Composable () -> Unit,
) {
    val colorScheme = when (themeMode) {
        KoloThemeMode.DAY -> DayColorScheme
        KoloThemeMode.SEPIA -> SepiaColorScheme
        KoloThemeMode.NIGHT -> NightColorScheme
    }

    val extraColors = when (themeMode) {
        KoloThemeMode.DAY -> KoloExtraColors(
            readerBackground = DayBackground,
            readerOnBackground = DayOnBackground,
        )
        KoloThemeMode.SEPIA -> KoloExtraColors(
            readerBackground = SepiaBackground,
            readerOnBackground = SepiaOnBackground,
            cream = SepiaSurfaceVariant,
        )
        KoloThemeMode.NIGHT -> KoloExtraColors(
            maroon = NightPrimary,
            maroonPale = MaroonDark,
            gold = NightSecondary,
            ink = NightOnBackground,
            inkMuted = NightOnSurfaceVariant,
            inkFaint = Color(0xFF607068),
            cream = NightSurfaceVariant,
            border = Color(0x33AABBAA),
            borderStrong = Color(0x4DAABBAA),
            readerBackground = NightBackground,
            readerOnBackground = NightOnBackground,
        )
    }

    val strings = if (language == "ml") malayalamStrings() else englishStrings()

    CompositionLocalProvider(
        LocalKoloColors provides extraColors,
        LocalKoloStrings provides strings,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = KoloTypography,
            shapes = KoloShapes,
            content = content,
        )
    }
}

// Convenience accessor
object KoloThemeExtras {
    val colors: KoloExtraColors
        @Composable get() = LocalKoloColors.current
}
