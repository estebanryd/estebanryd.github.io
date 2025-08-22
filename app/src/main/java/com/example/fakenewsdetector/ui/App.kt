package com.example.fakenewsdetector.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.fakenewsdetector.viewmodel.DetectorViewModel

@Composable
fun FakeNewsDetectorApp(viewModel: DetectorViewModel) {
	AppTheme {
		val uiState by viewModel.uiState.collectAsState()
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(16.dp)
				.verticalScroll(rememberScrollState()),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Top
		) {
			Text(
				text = "Fake News Detector",
				style = MaterialTheme.typography.headlineMedium
			)
			Spacer(Modifier.height(16.dp))
			BasicTextField(
				value = uiState.inputText,
				onValueChange = viewModel::onTextChanged,
				textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
				modifier = Modifier
					.fillMaxWidth()
					.height(160.dp)
			)
			Spacer(Modifier.height(12.dp))
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Button(onClick = viewModel::analyzeText, enabled = !uiState.isLoading) {
					Text("Analyze")
				}
				if (uiState.isLoading) {
					CircularProgressIndicator()
				}
			}
			Spacer(Modifier.height(16.dp))
			uiState.result?.let { result ->
				Text("Result: ${result.label}", style = MaterialTheme.typography.titleMedium)
				Spacer(Modifier.height(8.dp))
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text("Fake probability: ")
					val p = result.fakeProbability.coerceIn(0f, 1f)
					Slider(value = p, onValueChange = {}, enabled = false, modifier = Modifier.weight(1f))
					Text(String.format("%.0f%%", p * 100))
				}
				Spacer(Modifier.height(8.dp))
				Text("Reasons:")
				result.reasons.forEach { reason ->
					Text("- $reason")
				}
			}
		}
	}
}