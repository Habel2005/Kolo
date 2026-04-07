package com.kolo.prayer.data.model

data class UserProgress(
    val bookId: String,
    val lastSectionId: String? = null,
    val lastVerseNum: Int = 0,
    val lastOpenedTs: Long = 0L,
    val completedSections: Set<String> = emptySet(),
    val bookmarkSectionId: String? = null,
    val bookmarkVerseNum: Int = 0,
)

data class UserPreferences(
    val fontFamily: String = "NotoSerifMalayalam",
    val fontSizeSp: Int = 16,
    val lineHeightMultiplier: Float = 2.0f,
    val theme: String = "day",
    val language: String = "ml",
    val keepScreenOn: Boolean = true,
    val volumeScroll: Boolean = true,
    val onboardingDone: Boolean = false,
)
