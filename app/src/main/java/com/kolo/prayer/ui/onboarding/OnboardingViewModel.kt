package com.kolo.prayer.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolo.prayer.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingUiState(
    val currentStep: Int = 0,
    val selectedLanguage: String = "ml",
    val fontSizeSp: Int = 18,
    val lineHeightMultiplier: Float = 2.0f,
    val selectedTheme: String = "day",
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun nextStep() {
        _uiState.update { it.copy(currentStep = (it.currentStep + 1).coerceAtMost(2)) }
    }

    fun prevStep() {
        _uiState.update { it.copy(currentStep = (it.currentStep - 1).coerceAtLeast(0)) }
    }

    fun selectLanguage(lang: String) {
        _uiState.update { it.copy(selectedLanguage = lang) }
    }

    fun setFontSize(sizeSp: Int) {
        _uiState.update { it.copy(fontSizeSp = sizeSp.coerceIn(12, 28)) }
    }

    fun setLineHeight(multiplier: Float) {
        _uiState.update { it.copy(lineHeightMultiplier = multiplier.coerceIn(1.4f, 2.6f)) }
    }

    fun selectTheme(theme: String) {
        _uiState.update { it.copy(selectedTheme = theme) }
    }

    fun completeOnboarding(onDone: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            preferencesRepository.updateFontFamily("NotoSerifMalayalam")
            preferencesRepository.updateFontSize(state.fontSizeSp)
            preferencesRepository.updateLineHeight(state.lineHeightMultiplier)
            preferencesRepository.updateTheme(state.selectedTheme)
            preferencesRepository.updateLanguage(state.selectedLanguage)
            preferencesRepository.setOnboardingDone()
            onDone()
        }
    }
}
