package com.kolo.prayer.ui.reader

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolo.prayer.data.model.Book
import com.kolo.prayer.data.model.Section
import com.kolo.prayer.data.model.Verse
import com.kolo.prayer.data.repository.BookRepository
import com.kolo.prayer.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReaderUiState(
    val book: Book? = null,
    val currentSection: Section? = null,
    val currentSectionIndex: Int = 0,
    val totalSections: Int = 0,
    val bookmarkVerseNum: Int? = null,
    val highlightedVerseNum: Int? = null,
    val showBookmarkToast: Boolean = false,
    val showDrawer: Boolean = false,
    val fontSizeSp: Int = 16,
    val lineHeightMultiplier: Float = 2.0f,
    val theme: String = "day",
    val keepScreenOn: Boolean = true,
    val isLoading: Boolean = true,
)

@HiltViewModel
class ReaderViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val preferencesRepository: PreferencesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val bookId: String = savedStateHandle.get<String>("bookId") ?: ""
    private val sectionId: String = savedStateHandle.get<String>("sectionId") ?: ""

    private val _uiState = MutableStateFlow(ReaderUiState())
    val uiState: StateFlow<ReaderUiState> = _uiState.asStateFlow()

    init {
        loadSection()
        observePreferences()
    }

    private fun loadSection() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val book = bookRepository.getBook(bookId)
            if (book == null) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }
            
            val sectionIndex = book.sections.indexOfFirst { it.id == sectionId }
                .takeIf { it >= 0 } ?: 0
            val section = book.sections[sectionIndex]

            _uiState.update {
                it.copy(
                    book = book,
                    currentSection = section,
                    currentSectionIndex = sectionIndex,
                    totalSections = book.sections.size,
                    isLoading = false,
                )
            }
            
            // Mark state as read and bookmark it as last open point
            preferencesRepository.markSectionComplete(bookId, section.id)
            if (_uiState.value.bookmarkVerseNum == null) {
                preferencesRepository.setBookmark(bookId, section.id, 0)
            }
        }
    }

    private fun observePreferences() {
        viewModelScope.launch {
            preferencesRepository.userPreferences.collect { prefs ->
                _uiState.update {
                    it.copy(
                        fontSizeSp = prefs.fontSizeSp,
                        lineHeightMultiplier = prefs.lineHeightMultiplier,
                        theme = prefs.theme,
                        keepScreenOn = prefs.keepScreenOn,
                    )
                }
            }
        }
    }

    fun onLongPressVerse(verseNum: Int) {
        _uiState.update {
            it.copy(
                highlightedVerseNum = verseNum,
                bookmarkVerseNum = verseNum,
                showBookmarkToast = true,
            )
        }
        // Save bookmark
        viewModelScope.launch {
            val section = _uiState.value.currentSection ?: return@launch
            preferencesRepository.setBookmark(bookId, section.id, verseNum)
            delay(3000)
            _uiState.update { it.copy(showBookmarkToast = false) }
        }
    }

    fun undoBookmark() {
        _uiState.update {
            it.copy(
                highlightedVerseNum = null,
                showBookmarkToast = false,
            )
        }
    }

    fun toggleDrawer() {
        _uiState.update { it.copy(showDrawer = !it.showDrawer) }
    }

    fun closeDrawer() {
        _uiState.update { it.copy(showDrawer = false) }
    }

    fun navigateToSection(sectionId: String) {
        val book = _uiState.value.book ?: return
        val index = book.sections.indexOfFirst { it.id == sectionId }
            .takeIf { it >= 0 } ?: return
        val section = book.sections[index]

        _uiState.update {
            it.copy(
                currentSection = section,
                currentSectionIndex = index,
                bookmarkVerseNum = null,
                highlightedVerseNum = null,
                showDrawer = false,
            )
        }
        
        viewModelScope.launch {
            preferencesRepository.markSectionComplete(book.id, section.id)
            preferencesRepository.setBookmark(book.id, section.id, 0)
        }
    }

    fun navigateToNextSection() {
        val book = _uiState.value.book ?: return
        val nextIndex = _uiState.value.currentSectionIndex + 1
        if (nextIndex < book.sections.size) {
            navigateToSection(book.sections[nextIndex].id)
        }
    }

    fun updateTheme(theme: String) {
        viewModelScope.launch {
            preferencesRepository.updateTheme(theme)
        }
    }

    fun updateFontSize(sizeSp: Int) {
        viewModelScope.launch {
            preferencesRepository.updateFontSize(sizeSp)
        }
    }
}
