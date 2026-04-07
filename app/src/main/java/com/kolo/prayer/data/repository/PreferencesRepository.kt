package com.kolo.prayer.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.kolo.prayer.data.model.UserPreferences
import com.kolo.prayer.data.model.UserProgress
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kolo_prefs")

@Singleton
class PreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    // ── Preference Keys ──
    private object Keys {
        val FONT_FAMILY = stringPreferencesKey("font_family")
        val FONT_SIZE_SP = intPreferencesKey("font_size_sp")
        val LINE_HEIGHT_MULT = floatPreferencesKey("line_height_multiplier")
        val THEME = stringPreferencesKey("theme")
        val LANGUAGE = stringPreferencesKey("language")
        val KEEP_SCREEN_ON = booleanPreferencesKey("keep_screen_on")
        val VOLUME_SCROLL = booleanPreferencesKey("volume_scroll")
        val ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")

        // Per-book progress stored as JSON string keyed by book_id
        fun progressKey(bookId: String) = stringPreferencesKey("progress_$bookId")
    }

    private val json = Json { ignoreUnknownKeys = true }

    // ── User Preferences ──
    val userPreferences: Flow<UserPreferences> = context.dataStore.data.map { prefs ->
        UserPreferences(
            fontFamily = prefs[Keys.FONT_FAMILY] ?: "NotoSerifMalayalam",
            fontSizeSp = prefs[Keys.FONT_SIZE_SP] ?: 16,
            lineHeightMultiplier = prefs[Keys.LINE_HEIGHT_MULT] ?: 2.0f,
            theme = prefs[Keys.THEME] ?: "day",
            language = prefs[Keys.LANGUAGE] ?: "ml",
            keepScreenOn = prefs[Keys.KEEP_SCREEN_ON] ?: true,
            volumeScroll = prefs[Keys.VOLUME_SCROLL] ?: true,
            onboardingDone = prefs[Keys.ONBOARDING_DONE] ?: false,
        )
    }

    suspend fun updateFontFamily(family: String) {
        context.dataStore.edit { it[Keys.FONT_FAMILY] = family }
    }

    suspend fun updateFontSize(sizeSp: Int) {
        context.dataStore.edit { it[Keys.FONT_SIZE_SP] = sizeSp }
    }

    suspend fun updateLineHeight(multiplier: Float) {
        context.dataStore.edit { it[Keys.LINE_HEIGHT_MULT] = multiplier }
    }

    suspend fun updateTheme(theme: String) {
        context.dataStore.edit { it[Keys.THEME] = theme }
    }

    suspend fun updateLanguage(language: String) {
        context.dataStore.edit { it[Keys.LANGUAGE] = language }
    }

    suspend fun setOnboardingDone() {
        context.dataStore.edit { it[Keys.ONBOARDING_DONE] = true }
    }

    suspend fun updateKeepScreenOn(enabled: Boolean) {
        context.dataStore.edit { it[Keys.KEEP_SCREEN_ON] = enabled }
    }

    // ── Reading Progress ──
    fun getProgress(bookId: String): Flow<UserProgress?> {
        return context.dataStore.data.map { prefs ->
            prefs[Keys.progressKey(bookId)]?.let { raw ->
                try {
                    json.decodeFromString<SerializableProgress>(raw).toUserProgress(bookId)
                } catch (_: Exception) { null }
            }
        }
    }

    suspend fun saveProgress(progress: UserProgress) {
        val serializable = SerializableProgress.from(progress)
        context.dataStore.edit { prefs ->
            prefs[Keys.progressKey(progress.bookId)] = json.encodeToString(serializable)
        }
    }

    suspend fun markSectionComplete(bookId: String, sectionId: String) {
        context.dataStore.edit { prefs ->
            val key = Keys.progressKey(bookId)
            val existing = prefs[key]?.let {
                try { json.decodeFromString<SerializableProgress>(it) } catch (_: Exception) { null }
            } ?: SerializableProgress()

            val updated = existing.copy(
                completedSections = existing.completedSections + sectionId
            )
            prefs[key] = json.encodeToString(updated)
        }
    }

    suspend fun setBookmark(bookId: String, sectionId: String, verseNum: Int) {
        context.dataStore.edit { prefs ->
            val key = Keys.progressKey(bookId)
            val existing = prefs[key]?.let {
                try { json.decodeFromString<SerializableProgress>(it) } catch (_: Exception) { null }
            } ?: SerializableProgress()

            val updated = existing.copy(
                lastSectionId = sectionId,
                lastVerseNum = verseNum,
                lastOpenedTs = System.currentTimeMillis(),
                bookmarkSectionId = sectionId,
                bookmarkVerseNum = verseNum,
            )
            prefs[key] = json.encodeToString(updated)
        }
    }
}

@kotlinx.serialization.Serializable
private data class SerializableProgress(
    val lastSectionId: String? = null,
    val lastVerseNum: Int = 0,
    val lastOpenedTs: Long = 0L,
    val completedSections: Set<String> = emptySet(),
    val bookmarkSectionId: String? = null,
    val bookmarkVerseNum: Int = 0,
) {
    fun toUserProgress(bookId: String) = UserProgress(
        bookId = bookId,
        lastSectionId = lastSectionId,
        lastVerseNum = lastVerseNum,
        lastOpenedTs = lastOpenedTs,
        completedSections = completedSections,
        bookmarkSectionId = bookmarkSectionId,
        bookmarkVerseNum = bookmarkVerseNum,
    )

    companion object {
        fun from(p: UserProgress) = SerializableProgress(
            lastSectionId = p.lastSectionId,
            lastVerseNum = p.lastVerseNum,
            lastOpenedTs = p.lastOpenedTs,
            completedSections = p.completedSections,
            bookmarkSectionId = p.bookmarkSectionId,
            bookmarkVerseNum = p.bookmarkVerseNum,
        )
    }
}
