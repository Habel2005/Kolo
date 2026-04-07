package com.kolo.prayer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kolo.prayer.R



// ── Malayalam Font Families ──
// We use the single variable font file for all weights
val NotoSerifMalayalam = FontFamily(
    Font(R.font.noto_serif_malayalam, FontWeight.Normal),
    Font(R.font.noto_serif_malayalam, FontWeight.Medium),
    Font(R.font.noto_serif_malayalam, FontWeight.SemiBold),
    Font(R.font.noto_serif_malayalam, FontWeight.Bold),
)

// Placeholder families
val RachanaFamily = NotoSerifMalayalam
val MeeraFamily = NotoSerifMalayalam
val ChilankaFamily = NotoSerifMalayalam

// ── UI Font (EB Garamond Variable) ──
val EBGaramond = FontFamily(
    Font(R.font.eb_garamond, FontWeight.Normal),
    Font(R.font.eb_garamond, FontWeight.Medium),
    Font(R.font.eb_garamond, FontWeight.SemiBold),
)

// ── Typography Scale ──
val KoloTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = NotoSerifMalayalam,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        color = Maroon,
    ),
    headlineLarge = TextStyle(
        fontFamily = NotoSerifMalayalam,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 30.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = NotoSerifMalayalam,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = NotoSerifMalayalam,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = EBGaramond,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = EBGaramond,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = EBGaramond,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 18.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = NotoSerifMalayalam,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 32.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = NotoSerifMalayalam,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = NotoSerifMalayalam,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.3.sp,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.8.sp,
    ),
)