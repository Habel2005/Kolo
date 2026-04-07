package com.kolo.prayer.ui.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolo.prayer.ui.components.*
import com.kolo.prayer.ui.theme.*

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isMl = uiState.selectedLanguage == "ml"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        AnimatedContent(
            targetState = uiState.currentStep,
            modifier = Modifier.weight(1f),
            transitionSpec = {
                slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
            },
            label = "onboarding_step",
        ) { step ->
            when (step) {
                0 -> WelcomeStep(uiState, viewModel)
                1 -> ReadingComfortStep(uiState, viewModel)
                2 -> ReadyStep(uiState)
            }
        }

        // Bottom: dots + CTA
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            StepIndicator(
                stepCount = 3,
                currentStep = uiState.currentStep,
                modifier = Modifier.padding(bottom = 12.dp),
            )
            KoloPrimaryButton(
                text = when {
                    uiState.currentStep == 2 && isMl -> "ആപ്പിലേക്ക് പ്രവേശിക്കുക ✦"
                    uiState.currentStep == 2 -> "Enter the App ✦"
                    isMl -> "തുടരുക →"
                    else -> "Continue →"
                },
                onClick = {
                    if (uiState.currentStep < 2) {
                        viewModel.nextStep()
                    } else {
                        viewModel.completeOnboarding(onComplete)
                    }
                },
                modifier = Modifier.padding(bottom = 20.dp),
            )
        }
    }
}

@Composable
private fun WelcomeStep(
    state: OnboardingUiState,
    viewModel: OnboardingViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(0.15f))

        // Hero
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CrossSymbol(size = 56.dp)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "ദൈവ ഭക്തി പ്രാർത്ഥന",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                ),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "JACOBITE SYRIAN PRAYER",
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 1.5.sp,
                ),
                color = KoloThemeExtras.colors.inkFaint,
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Language selector
        Text(
            text = if (state.selectedLanguage == "ml") "ഭാഷ തിരഞ്ഞെടുക്കുക" else "Select Language",
            style = MaterialTheme.typography.labelMedium,
            color = KoloThemeExtras.colors.inkMuted,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            KoloChip("മലയാളം", state.selectedLanguage == "ml", { viewModel.selectLanguage("ml") }, useMalayalamFont = true)
            KoloChip("English", state.selectedLanguage == "en", { viewModel.selectLanguage("en") })
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Preview card
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
        ) {
            Text(
                text = if (state.selectedLanguage == "ml") "മാതൃക" else "PREVIEW",
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Medium,
                ),
                color = KoloThemeExtras.colors.inkFaint,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "കർത്താവേ, ഞങ്ങൾക്ക് കൃപ ചെയ്യേണമേ.",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Cream, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = NotoSerifMalayalam,
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                ),
                color = Ink,
            )
        }

        Spacer(modifier = Modifier.weight(0.2f))
    }
}

@Composable
private fun ReadingComfortStep(
    state: OnboardingUiState,
    viewModel: OnboardingViewModel,
) {
    val isMl = state.selectedLanguage == "ml"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Cream)
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = if (isMl) "ഘട്ടം 2 / 3" else "STEP 2 OF 3",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                color = KoloThemeExtras.colors.inkFaint,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isMl) "വായനാ സൗകര്യം" else "Reading Comfort",
                style = MaterialTheme.typography.titleLarge,
                color = Ink,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = if (isMl) "നിങ്ങൾക്ക് ഇഷ്ടമുള്ളത് തിരഞ്ഞെടുക്കുക" else "Adjust to what feels right for you",
                style = MaterialTheme.typography.labelMedium,
                color = KoloThemeExtras.colors.inkFaint,
            )
        }

        // Font size card
        OnboardingCard {
            Text(
                text = if (isMl) "ഫോണ്ട് സൈസ്" else "FONT SIZE",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp, fontWeight = FontWeight.Medium),
                color = KoloThemeExtras.colors.inkFaint,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Slider(
                value = state.fontSizeSp.toFloat(),
                onValueChange = { viewModel.setFontSize(it.toInt()) },
                valueRange = 12f..28f,
                colors = SliderDefaults.colors(
                    thumbColor = Maroon,
                    activeTrackColor = Maroon,
                    inactiveTrackColor = KoloThemeExtras.colors.border,
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "കർത്താവേ, ഞങ്ങളെ അനുഗ്രഹിക്കേണമേ. ഞങ്ങളുടെ പ്രാർത്ഥന കേൾക്കേണമേ.",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Cream, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = NotoSerifMalayalam,
                    fontSize = state.fontSizeSp.sp,
                    lineHeight = (state.fontSizeSp * state.lineHeightMultiplier).sp,
                ),
                color = Ink,
            )
        }

        // Line spacing card
        OnboardingCard {
            Text(
                text = if (isMl) "വരി അകലം" else "LINE SPACING",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp, fontWeight = FontWeight.Medium),
                color = KoloThemeExtras.colors.inkFaint,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Slider(
                value = state.lineHeightMultiplier,
                onValueChange = { viewModel.setLineHeight(it) },
                valueRange = 1.4f..2.6f,
                colors = SliderDefaults.colors(
                    thumbColor = Maroon,
                    activeTrackColor = Maroon,
                    inactiveTrackColor = KoloThemeExtras.colors.border,
                ),
            )
        }

        // Theme card
        OnboardingCard {
            Text(
                text = if (isMl) "തീം" else "THEME",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp, fontWeight = FontWeight.Medium),
                color = KoloThemeExtras.colors.inkFaint,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ThemeOption("☀ Day", state.selectedTheme == "day", DayBackground, Ink, { viewModel.selectTheme("day") }, Modifier.weight(1f))
                ThemeOption("📜 Sepia", state.selectedTheme == "sepia", SepiaBackground, SepiaOnBackground, { viewModel.selectTheme("sepia") }, Modifier.weight(1f))
                ThemeOption("🌙 Night", state.selectedTheme == "night", NightBackground, NightOnBackground, { viewModel.selectTheme("night") }, Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ReadyStep(state: OnboardingUiState) {
    val isMl = state.selectedLanguage == "ml"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Icon box
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Maroon),
            contentAlignment = Alignment.Center,
        ) {
            CrossSymbol(
                size = 28.dp,
                color = Color.White,
                barWidth = 6.dp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = if (isMl) "സ്വാഗതം" else "Welcome",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = NotoSerifMalayalam,
                color = Maroon,
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isMl) "എല്ലാം തയ്യാർ. നിങ്ങളുടെ ക്രമീകരണങ്ങൾ\nസേവ് ചെയ്തിരിക്കുന്നു."
                   else "You're all set. Your preferences are saved\nand can be changed anytime in settings.",
            style = MaterialTheme.typography.labelLarge.copy(lineHeight = 20.sp),
            color = KoloThemeExtras.colors.inkFaint,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Settings summary card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text(
                text = if (isMl) "നിങ്ങളുടെ ക്രമീകരണങ്ങൾ" else "YOUR SETTINGS",
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Medium,
                ),
                color = KoloThemeExtras.colors.inkFaint,
            )
            Spacer(modifier = Modifier.height(12.dp))
            SettingsRow(
                if (isMl) "ഭാഷ" else "Language",
                if (isMl) "മലയാളം" else "English",
            )
            Spacer(modifier = Modifier.height(8.dp))
            SettingsRow(
                if (isMl) "ഫോണ്ട് സൈസ്" else "Font size",
                "${state.fontSizeSp}sp",
            )
            Spacer(modifier = Modifier.height(8.dp))
            SettingsRow(
                if (isMl) "തീം" else "Theme",
                state.selectedTheme.replaceFirstChar { it.uppercase() },
            )
        }
    }
}

@Composable
private fun SettingsRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Ink,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = Maroon,
        )
    }
}

@Composable
private fun OnboardingCard(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        content = content,
    )
}

@Composable
private fun ThemeOption(
    label: String,
    isActive: Boolean,
    bgColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .then(
                if (isActive) Modifier.border(2.dp, Maroon, RoundedCornerShape(8.dp))
                else Modifier.border(0.5.dp, KoloThemeExtras.colors.border, RoundedCornerShape(8.dp))
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
        )
    }
}
