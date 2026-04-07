package com.kolo.prayer.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val KoloShapes = Shapes(
    // Small elements — chips, badges
    small = RoundedCornerShape(20.dp),
    // Medium elements — cards, buttons, inputs
    medium = RoundedCornerShape(12.dp),
    // Large elements — bottom sheets, dialogs
    large = RoundedCornerShape(16.dp),
    // Extra large — full rounding for pill buttons
    extraLarge = RoundedCornerShape(28.dp),
)
