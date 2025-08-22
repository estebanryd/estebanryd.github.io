package com.example.fakenewsdetector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.fakenewsdetector.ui.FakeNewsDetectorApp
import com.example.fakenewsdetector.viewmodel.DetectorViewModel

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val viewModel: DetectorViewModel by viewModels()
		setContent {
			FakeNewsDetectorApp(viewModel = viewModel)
		}
	}
}