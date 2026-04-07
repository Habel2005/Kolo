package com.kolo.prayer.ui.theme

import androidx.compose.ui.graphics.Color

// ── Liturgical Palette ──
// Drawn from Syrian Orthodox church aesthetics:
// Maroon = bishop's vestment, Gold = sacred vessels, Cream = manuscript vellum

val Maroon = Color(0xFF7A1E2E)
val MaroonLight = Color(0xFF9B3040)
val MaroonDark = Color(0xFF5C1522)
val MaroonPale = Color(0xFFF7EDEF)

val Gold = Color(0xFFC8901A)
val GoldLight = Color(0xFFE8B84B)
val GoldPale = Color(0xFFFDF6E3)

val Cream = Color(0xFFFAF7F0)
val CreamDark = Color(0xFFF0E8D8)

val Ink = Color(0xFF1C1410)
val InkMuted = Color(0xFF5A4A40)
val InkFaint = Color(0xFF9A8A7F)

val SurfaceWhite = Color(0xFFFFFFFF)
val Border = Color(0x265C321E)       // rgba(92,50,30,0.15)
val BorderStrong = Color(0x4D5C321E) // rgba(92,50,30,0.3)

// ── Category Colors ──
val CategoryDaily = Maroon           // Daily Offices (Shoymto)
val CategoryLiturgical = Color(0xFF185FA5)  // Liturgical (Qurbana) — Blue
val CategoryPenitential = Color(0xFF0F6E56) // Penitential — Green
val CategorySacramental = Color(0xFF854F0B) // Sacramental — Amber
val CategorySpecial = Color(0xFF534AB7)     // Special Rites — Purple

// ── Day Theme ──
val DayBackground = Color(0xFFFAF8F3)
val DayOnBackground = Ink
val DaySurface = SurfaceWhite
val DayOnSurface = Ink
val DaySurfaceVariant = Cream
val DayOnSurfaceVariant = InkMuted

// ── Sepia Theme ──
val SepiaBackground = Color(0xFFF5EDDA)
val SepiaOnBackground = Color(0xFF4A3728)
val SepiaSurface = Color(0xFFF0E6CF)
val SepiaOnSurface = Color(0xFF4A3728)
val SepiaSurfaceVariant = Color(0xFFEDE4CF)
val SepiaOnSurfaceVariant = Color(0xFF7B6348)

// ── Night Theme ──
val NightBackground = Color(0xFF1C2420)
val NightOnBackground = Color(0xFFD4C8B0)
val NightSurface = Color(0xFF232E2A)
val NightOnSurface = Color(0xFFD4C8B0)
val NightSurfaceVariant = Color(0xFF2A3530)
val NightOnSurfaceVariant = Color(0xFF8A9E94)
val NightPrimary = Color(0xFFCF8A9A)
val NightSecondary = Color(0xFFE8B84B)
