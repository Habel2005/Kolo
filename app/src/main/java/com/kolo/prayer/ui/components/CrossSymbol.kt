package com.kolo.prayer.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kolo.prayer.ui.theme.Maroon

/**
 * Syrian Orthodox cross symbol — vertical + horizontal bars.
 * Used in onboarding and branding.
 */
@Composable
fun CrossSymbol(
    modifier: Modifier = Modifier,
    size: Dp = 52.dp,
    color: Color = Maroon,
    barWidth: Dp = 10.dp,
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size.width
        val barW = barWidth.toPx()
        val cornerR = CornerRadius(3.dp.toPx(), 3.dp.toPx())

        // Vertical bar
        drawRoundRect(
            color = color,
            topLeft = Offset((canvasSize - barW) / 2f, 0f),
            size = Size(barW, canvasSize),
            cornerRadius = cornerR,
        )

        // Horizontal bar — positioned about 27% from the top
        val hTop = canvasSize * 0.27f
        drawRoundRect(
            color = color,
            topLeft = Offset(0f, hTop),
            size = Size(canvasSize, barW),
            cornerRadius = cornerR,
        )
    }
}
