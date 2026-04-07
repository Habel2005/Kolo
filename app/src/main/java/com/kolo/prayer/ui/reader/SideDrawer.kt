package com.kolo.prayer.ui.reader

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolo.prayer.data.model.Book
import com.kolo.prayer.ui.theme.*

/**
 * Side drawer that slides in from the right — 80% width.
 * Contains: reading settings (font size + theme), TOC, and action links.
 */
@Composable
fun SideDrawer(
    book: Book,
    currentSectionId: String,
    completedSections: Set<String>,
    fontSizeSp: Int,
    currentTheme: String,
    onClose: () -> Unit,
    onSectionClick: (sectionId: String) -> Unit,
    onThemeChange: (String) -> Unit,
    onFontSizeChange: (Int) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Scrim
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x731C1410))
                .clickable(onClick = onClose)
        )

        // Drawer panel — 80% width from right
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.80f)
                .align(Alignment.CenterEnd)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // ── Header ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Maroon)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column {
                    Text(
                        text = "Table of Contents",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = NotoSerifMalayalam,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                        ),
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = book.titleEn,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.6f),
                    )
                }

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .clickable(onClick = onClose),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(12.dp),
                    )
                }
            }

            // ── Reading Settings ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "READING SETTINGS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    color = KoloThemeExtras.colors.inkFaint,
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Font size slider
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Text size",
                        style = MaterialTheme.typography.labelSmall,
                        color = KoloThemeExtras.colors.inkFaint,
                        modifier = Modifier.width(60.dp),
                    )
                    Slider(
                        value = fontSizeSp.toFloat(),
                        onValueChange = { onFontSizeChange(it.toInt()) },
                        valueRange = 12f..28f,
                        modifier = Modifier.weight(1f),
                        colors = SliderDefaults.colors(
                            thumbColor = Maroon,
                            activeTrackColor = Maroon,
                            inactiveTrackColor = KoloThemeExtras.colors.border,
                        ),
                    )
                }

                // Theme toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Theme",
                        style = MaterialTheme.typography.labelSmall,
                        color = KoloThemeExtras.colors.inkFaint,
                        modifier = Modifier.width(60.dp),
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.weight(1f),
                    ) {
                        DrawerThemeChip("Day", "day", DayBackground, Ink, currentTheme, onThemeChange, Modifier.weight(1f))
                        DrawerThemeChip("Sepia", "sepia", SepiaBackground, SepiaOnBackground, currentTheme, onThemeChange, Modifier.weight(1f))
                        DrawerThemeChip("Night", "night", NightBackground, NightOnBackground, currentTheme, onThemeChange, Modifier.weight(1f))
                    }
                }
            }

            HorizontalDivider(color = KoloThemeExtras.colors.border)

            // ── TOC List ──
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 8.dp)
            ) {
                var lastCategory = ""
                book.sections.forEach { section ->
                    if (section.categoryLabel != lastCategory) {
                        lastCategory = section.categoryLabel
                        Text(
                            text = lastCategory.uppercase(),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                letterSpacing = 1.sp,
                                fontWeight = FontWeight.Medium,
                                fontSize = 9.sp,
                            ),
                            color = KoloThemeExtras.colors.inkFaint,
                        )
                    }

                    val isCurrent = section.id == currentSectionId
                    val isCompleted = section.id in completedSections

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (isCurrent) {
                                    Modifier.background(MaroonPale)
                                } else Modifier
                            )
                            .clickable { onSectionClick(section.id) }
                            .padding(horizontal = 14.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "${section.sortOrder}",
                            style = MaterialTheme.typography.labelSmall,
                            color = KoloThemeExtras.colors.inkFaint,
                            modifier = Modifier.width(16.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = section.titleMl,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = NotoSerifMalayalam,
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                            ),
                            color = if (isCurrent) Maroon else Ink,
                            fontWeight = if (isCurrent) FontWeight.Medium else FontWeight.Normal,
                            modifier = Modifier.weight(1f),
                        )
                        if (isCurrent) {
                            Text(
                                text = "NOW",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 9.sp,
                                ),
                                color = Maroon,
                            )
                        } else if (isCompleted) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Maroon)
                            )
                        }
                    }
                }
            }

            HorizontalDivider(color = KoloThemeExtras.colors.border)

            // ── Actions ──
            Column(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                DrawerAction(
                    icon = Icons.Default.Star,
                    label = "Bookmarks & Notes",
                    onClick = {},
                )
                Spacer(modifier = Modifier.height(6.dp))
                DrawerAction(
                    icon = Icons.Default.Info,
                    label = "About this Prayer",
                    onClick = {},
                )
            }
        }
    }
}

@Composable
private fun DrawerThemeChip(
    label: String,
    themeId: String,
    bgColor: Color,
    textColor: Color,
    currentTheme: String,
    onThemeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isActive = themeId == currentTheme
    Box(
        modifier = modifier
            .height(22.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(bgColor)
            .then(
                if (isActive) Modifier.border(1.5.dp, Maroon, RoundedCornerShape(5.dp))
                else Modifier
            )
            .clickable { onThemeChange(themeId) },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Medium),
            color = textColor,
        )
    }
}

@Composable
private fun DrawerAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 8.dp),
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
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Ink,
        )
    }
}
