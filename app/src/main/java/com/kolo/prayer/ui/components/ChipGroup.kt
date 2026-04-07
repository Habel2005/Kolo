package com.kolo.prayer.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolo.prayer.ui.theme.*

/**
 * Chip for font/language/theme selection — pill-shaped.
 */
@Composable
fun KoloChip(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    useMalayalamFont: Boolean = false,
) {
    Text(
        text = label,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isActive) Maroon else MaterialTheme.colorScheme.surface
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        style = if (useMalayalamFont) {
            MaterialTheme.typography.bodySmall.copy(
                fontFamily = NotoSerifMalayalam,
                fontSize = 13.sp,
            )
        } else {
            MaterialTheme.typography.labelMedium
        },
        color = if (isActive) Color.White else KoloThemeExtras.colors.inkMuted,
    )
}

/**
 * Step indicator dots — current step is a maroon pill, others are circles.
 */
@Composable
fun StepIndicator(
    stepCount: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(stepCount) { index ->
            val isActive = index == currentStep
            val width by animateDpAsState(
                targetValue = if (isActive) 18.dp else 6.dp,
                label = "dot_width",
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 2.5.dp)
                    .height(6.dp)
                    .width(width)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        if (isActive) Maroon else KoloThemeExtras.colors.borderStrong
                    )
            )
        }
    }
}

/**
 * Primary CTA button — full-width, maroon, rounded.
 */
@Composable
fun KoloPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Maroon)
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.3.sp,
        )
    }
}
