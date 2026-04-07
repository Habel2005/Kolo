package com.kolo.prayer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolo.prayer.data.model.BookSummary
import com.kolo.prayer.data.repository.BookRepository
import com.kolo.prayer.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val books: List<BookSummary> = emptyList(),
    val filteredBooks: List<BookSummary> = emptyList(),
    val recentBooks: List<RecentBookInfo> = emptyList(),
    val greeting: String = "ശുഭദിനം",
    val greetingEn: String = "Good morning",
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val isListView: Boolean = false,
    val language: String = "ml",
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
)

data class RecentBookInfo(
    val bookId: String,
    val titleMl: String,
    val subtitleEn: String,
    val symbol: String,
    val colorHex: String,
    val progress: Float,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadBooks()
        observePreferences()
    }

    private fun observePreferences() {
        viewModelScope.launch {
            preferencesRepository.userPreferences.collect { prefs ->
                _uiState.update { it.copy(language = prefs.language) }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            // Clear repository caches if needed, but since we didn't add clearCache, we'll just reload.
            // bookRepository.clearCache() // To force fresh network fetch
            loadBooksInternal()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    private fun loadBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            loadBooksInternal()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun loadBooksInternal() {
        // We'll read from repository. If the repo cached it, it's fast. 
        // We should add a forceRefresh to the repository to actually fetch again if requested.
        val books = bookRepository.getAllBooks(forceRefresh = _uiState.value.isRefreshing)

        val recentBooks = books.take(3).map { b ->
            val userProgress = preferencesRepository.getProgress(b.id).firstOrNull()
            val completedCount = userProgress?.completedSections?.size ?: 0
            val progress = if (b.sectionCount > 0) completedCount.toFloat() / b.sectionCount else 0f
            
            RecentBookInfo(
                bookId = b.id,
                titleMl = b.titleMl,
                subtitleEn = b.titleEn,
                symbol = b.symbol,
                colorHex = b.colorHex,
                progress = progress.coerceIn(0f, 1f)
            )
        }

        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        val (mlGreet, enGreet) = when {
            hour < 12 -> "ശുഭദിനം" to "Good morning"
            hour < 17 -> "ശുഭ അപരാഹ്നം" to "Good afternoon"
            else -> "ശുഭ സന്ധ്യ" to "Good evening"
        }

        _uiState.update {
            it.copy(
                books = books,
                filteredBooks = books,
                recentBooks = recentBooks,
                greeting = mlGreet,
                greetingEn = enGreet,
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { state ->
            val filtered = if (query.isBlank()) {
                state.books
            } else {
                state.books.filter { book ->
                    book.titleMl.contains(query, ignoreCase = true) ||
                    book.titleEn.contains(query, ignoreCase = true) ||
                    book.subtitle.contains(query, ignoreCase = true)
                }
            }
            state.copy(searchQuery = query, filteredBooks = filtered)
        }
    }

    fun toggleSearch() {
        _uiState.update {
            if (it.isSearchActive) {
                it.copy(isSearchActive = false, searchQuery = "", filteredBooks = it.books)
            } else {
                it.copy(isSearchActive = true)
            }
        }
    }

    fun toggleViewMode() {
        _uiState.update { it.copy(isListView = !it.isListView) }
    }
}
