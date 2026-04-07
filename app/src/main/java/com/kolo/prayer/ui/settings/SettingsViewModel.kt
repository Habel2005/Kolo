package com.kolo.prayer.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolo.prayer.data.model.UserPreferences
import com.kolo.prayer.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    val preferences: StateFlow<UserPreferences> = preferencesRepository.userPreferences
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserPreferences(),
        )

    fun updateFontFamily(family: String) {
        viewModelScope.launch { preferencesRepository.updateFontFamily(family) }
    }

    fun updateFontSize(sizeSp: Int) {
        viewModelScope.launch { preferencesRepository.updateFontSize(sizeSp) }
    }

    fun updateLineHeight(multiplier: Float) {
        viewModelScope.launch { preferencesRepository.updateLineHeight(multiplier) }
    }

    fun updateTheme(theme: String) {
        viewModelScope.launch { preferencesRepository.updateTheme(theme) }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch { preferencesRepository.updateLanguage(language) }
    }

    fun updateKeepScreenOn(enabled: Boolean) {
        viewModelScope.launch { preferencesRepository.updateKeepScreenOn(enabled) }
    }
}
