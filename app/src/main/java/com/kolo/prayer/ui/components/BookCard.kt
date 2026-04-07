package com.kolo.prayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolo.prayer.ui.theme.*

/**
 * Book card for the home screen 2-column grid.
 * Color-coded cover with pattern overlay, symbol, title, meta, and optional badge.
 */
@Composable
fun BookCard(
    titleMl: String,
    titleEn: String,
    sectionCount: Int,
    symbol: String,
    colorHex: String,
    category: String,
    isDaily: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val coverColor = try {
        Color(android.graphics.Color.parseColor(colorHex))
    } catch (_: Exception) { Maroon }

    val badgeText = when (category) {
        "daily_office" -> "Daily"
        "liturgical" -> "Liturgy"
        "penitential" -> "Penitential"
        "sacramental" -> "Sacramental"
        "special" -> "Special Rite"
        else -> null
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
    ) {
        // Cover
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(coverColor, coverColor.copy(alpha = 0.85f))
                    )
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = symbol,
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
            )
        }

        // Info
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Text(
                text = titleMl,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = NotoSerifMalayalam,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "$titleEn · $sectionCount sections",
                style = MaterialTheme.typography.labelSmall,
                color = KoloThemeExtras.colors.inkFaint,
            )
            if (badgeText != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = badgeText,
                    modifier = Modifier
                        .background(
                            MaroonPale,
                            shape = RoundedCornerShape(4.dp),
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                    color = Maroon,
                )
            }
        }
    }
}
