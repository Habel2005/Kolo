package com.kolo.prayer.ui.bookdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolo.prayer.data.model.Book
import com.kolo.prayer.data.model.BookSummary
import com.kolo.prayer.data.model.Section
import com.kolo.prayer.data.repository.BookRepository
import com.kolo.prayer.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookDetailUiState(
    val summary: BookSummary? = null,
    val book: Book? = null,
    val completedSections: Set<String> = emptySet(),
    val currentSectionId: String? = null,
    val currentProgress: Int = 0,
    val sectionsByCategory: Map<String, List<Section>> = emptyMap(),
    val isLoading: Boolean = true,
)

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val preferencesRepository: PreferencesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val bookId: String = savedStateHandle.get<String>("bookId") ?: ""

    private val _uiState = MutableStateFlow(BookDetailUiState())
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    init {
        loadBook()
    }

    private fun loadBook() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            val summary = bookRepository.getBookSummary(bookId)
            val book = bookRepository.getBook(bookId)
            
            if (book == null) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            val sectionsByCategory = book.sections
                .groupBy { it.categoryLabel.ifEmpty { "Other" } }
                .toSortedMap(compareBy { key ->
                    book.sections.indexOfFirst { it.categoryLabel == key }
                })

            preferencesRepository.getProgress(bookId).collectLatest { progressObj ->
                val completed = progressObj?.completedSections ?: emptySet()
                val currentProgress = if (book.sections.isNotEmpty()) {
                    ((completed.size.toFloat() / book.sections.size) * 100).toInt()
                } else 0
                
                val currentSecId = progressObj?.lastSectionId ?: book.sections.firstOrNull()?.id

                _uiState.update {
                    it.copy(
                        summary = summary,
                        book = book,
                        sectionsByCategory = sectionsByCategory,
                        currentSectionId = currentSecId,
                        currentProgress = currentProgress,
                        completedSections = completed,
                        isLoading = false
                    )
                }
            }
        }
    }
}
