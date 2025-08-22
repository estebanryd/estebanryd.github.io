package com.example.fakenewsdetector.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakenewsdetector.detection.DetectionResult
import com.example.fakenewsdetector.detection.HeuristicNewsDetector
import com.example.fakenewsdetector.detection.NewsDetector
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetectorViewModel(
	private val detector: NewsDetector = HeuristicNewsDetector()
) : ViewModel() {

	data class UiState(
		val inputText: String = "",
		val isLoading: Boolean = false,
		val result: DetectionResult? = null
	)

	private val _uiState = MutableStateFlow(UiState())
	val uiState: StateFlow<UiState> = _uiState.asStateFlow()

	fun onTextChanged(newText: String) {
		_uiState.value = _uiState.value.copy(inputText = newText)
	}

	fun analyzeText() {
		val text = _uiState.value.inputText
		viewModelScope.launch {
			_uiState.value = _uiState.value.copy(isLoading = true, result = null)
			// Simulate small delay to mimic network/model latency
			delay(300)
			val result = detector.analyze(text)
			_uiState.value = _uiState.value.copy(isLoading = false, result = result)
		}
	}
}