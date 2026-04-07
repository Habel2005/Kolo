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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolo.prayer.ui.theme.*

/**
 * Horizontal scroll card for "Continue Reading" section.
 * Shows book spine color, title, subtitle, and progress bar.
 */
@Composable
fun RecentCard(
    titleMl: String,
    subtitleEn: String,
    symbol: String,
    colorHex: String,
    progress: Float, // 0.0 to 1.0
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spineColor = try {
        Color(android.graphics.Color.parseColor(colorHex))
    } catch (_: Exception) { Maroon }

    Column(
        modifier = modifier
            .width(110.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        // Spine
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(spineColor),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = symbol,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontFamily = NotoSerifMalayalam,
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = titleMl,
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = NotoSerifMalayalam,
                fontSize = 10.sp,
                lineHeight = 14.sp,
            ),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = subtitleEn,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
            color = KoloThemeExtras.colors.inkFaint,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Progress bar
        KoloProgressBar(
            progress = progress,
            color = spineColor,
        )
    }
}

@Composable
fun KoloProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = Maroon,
    trackColor: Color = KoloThemeExtras.colors.border,
    height: Int = 2,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
            .clip(RoundedCornerShape(1.dp))
            .background(trackColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = progress.coerceIn(0f, 1f))
                .clip(RoundedCornerShape(1.dp))
                .background(color)
        )
    }
}
