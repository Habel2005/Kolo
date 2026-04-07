package com.kolo.prayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolo.prayer.ui.theme.*

/**
 * Table of Contents item for the Book Detail screen.
 * Shows section number, Malayalam+English title, completion/progress state.
 */
@Composable
fun TocItem(
    number: Int,
    titleMl: String,
    titleEn: String,
    isCurrent: Boolean,
    isCompleted: Boolean,
    progressPercent: Int? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(
                when {
                    isCurrent -> MaroonPale
                    else -> MaterialTheme.colorScheme.surface
                }
            )
            .then(
                if (isCurrent) {
                    Modifier
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Number badge
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(
                    if (isCurrent) Maroon else MaroonPale
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = number.toString(),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isCurrent) Color.White else Maroon,
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Titles
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = titleMl,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = NotoSerifMalayalam,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                ),
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = titleEn,
                style = MaterialTheme.typography.labelSmall,
                color = KoloThemeExtras.colors.inkFaint,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // State indicator
        when {
            isCompleted -> {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Maroon),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = Color.White,
                        modifier = Modifier.size(10.dp),
                    )
                }
            }
            isCurrent && progressPercent != null -> {
                Text(
                    text = "$progressPercent%",
                    modifier = Modifier
                        .background(MaroonPale, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Medium,
                    color = Maroon,
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Open",
                    tint = KoloThemeExtras.colors.inkFaint,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}

/**
 * Section header in the TOC (e.g., "Introduction", "Core Prayers", "Closing")
 */
@Composable
fun TocSectionHeader(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = label.uppercase(),
        modifier = modifier.padding(vertical = 12.dp),
        style = MaterialTheme.typography.labelSmall.copy(
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Medium,
        ),
        color = KoloThemeExtras.colors.inkFaint,
    )
}
