package com.kolo.prayer.ui.settings

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolo.prayer.ui.components.CrossSymbol
import com.kolo.prayer.ui.components.KoloChip
import com.kolo.prayer.ui.theme.*

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val prefs by viewModel.preferences.collectAsStateWithLifecycle()
    val strings = Strings.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Maroon)
                .statusBarsPadding()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.15f))
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(14.dp),
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = strings.settings,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                ),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // ── Appearance ──
            SettingsGroupHeader(strings.appearance)

            SettingsCard {
                Text(
                    text = strings.theme.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    color = KoloThemeExtras.colors.inkFaint,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ThemeChip("☀ Day", "day", prefs.theme) { viewModel.updateTheme("day") }
                    ThemeChip("📜 Sepia", "sepia", prefs.theme) { viewModel.updateTheme("sepia") }
                    ThemeChip("🌙 Night", "night", prefs.theme) { viewModel.updateTheme("night") }
                }
            }

            // ── Reading ──
            SettingsGroupHeader(strings.reading)

            SettingsCard {
                SettingsSliderItem(
                    label = "${strings.fontSize} — ${prefs.fontSizeSp}sp",
                    value = prefs.fontSizeSp.toFloat(),
                    onValueChange = { viewModel.updateFontSize(it.toInt()) },
                    valueRange = 12f..28f,
                )
                Spacer(modifier = Modifier.height(12.dp))
                SettingsSliderItem(
                    label = "${strings.lineSpacing} — ${"%.1f".format(prefs.lineHeightMultiplier)}x",
                    value = prefs.lineHeightMultiplier,
                    onValueChange = { viewModel.updateLineHeight(it) },
                    valueRange = 1.4f..2.6f,
                )
            }

            // ── Language ──
            SettingsGroupHeader(strings.language)

            SettingsCard {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    KoloChip(
                        label = "മലയാളം",
                        isActive = prefs.language == "ml",
                        onClick = { viewModel.updateLanguage("ml") },
                        useMalayalamFont = true,
                    )
                    KoloChip(
                        label = "English",
                        isActive = prefs.language == "en",
                        onClick = { viewModel.updateLanguage("en") },
                    )
                }
            }

            // ── General ──
            SettingsGroupHeader(strings.general)

            SettingsCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = strings.keepScreenOn,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Switch(
                        checked = prefs.keepScreenOn,
                        onCheckedChange = { viewModel.updateKeepScreenOn(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Maroon,
                            checkedTrackColor = MaroonPale,
                        ),
                    )
                }
            }

            // ── About ──
            SettingsGroupHeader(strings.about)

            SettingsCard {
                SettingsActionItem(
                    icon = Icons.Default.Share,
                    label = strings.shareApp,
                    onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "Check out Kolo — Jacobite Syrian Prayer App")
                        }
                        context.startActivity(Intent.createChooser(intent, "Share Kolo"))
                    },
                )
                HorizontalDivider(
                    color = KoloThemeExtras.colors.border,
                    modifier = Modifier.padding(vertical = 4.dp),
                )
                SettingsActionItem(
                    icon = Icons.Default.Star,
                    label = strings.rateApp,
                    onClick = { /* Rate on Play Store — Phase 2 */ },
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // App branding
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CrossSymbol(
                    size = 28.dp,
                    color = KoloThemeExtras.colors.inkFaint,
                    barWidth = 4.dp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Kolo · v1.0.0",
                    style = MaterialTheme.typography.labelSmall,
                    color = KoloThemeExtras.colors.inkFaint,
                )
                Text(
                    text = strings.appDescription,
                    style = MaterialTheme.typography.labelSmall,
                    color = KoloThemeExtras.colors.inkFaint,
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun SettingsGroupHeader(title: String) {
    Text(
        text = title.uppercase(),
        modifier = Modifier.padding(start = 4.dp, top = 16.dp, bottom = 6.dp),
        style = MaterialTheme.typography.labelSmall.copy(
            letterSpacing = 1.2.sp,
            fontWeight = FontWeight.Medium,
        ),
        color = KoloThemeExtras.colors.inkFaint,
    )
}

@Composable
private fun SettingsCard(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        content = content,
    )
}

@Composable
private fun SettingsSliderItem(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
) {
    Text(
        text = label.uppercase(),
        style = MaterialTheme.typography.labelSmall.copy(
            letterSpacing = 0.8.sp,
            fontWeight = FontWeight.Medium,
        ),
        color = KoloThemeExtras.colors.inkFaint,
    )
    Spacer(modifier = Modifier.height(4.dp))
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        colors = SliderDefaults.colors(
            thumbColor = Maroon,
            activeTrackColor = Maroon,
            inactiveTrackColor = KoloThemeExtras.colors.border,
        ),
    )
}

@Composable
private fun SettingsActionItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaroonPale),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Maroon,
                modifier = Modifier.size(14.dp),
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun ThemeChip(
    label: String,
    themeId: String,
    currentTheme: String,
    onClick: () -> Unit,
) {
    KoloChip(
        label = label,
        isActive = themeId == currentTheme,
        onClick = onClick,
    )
}
