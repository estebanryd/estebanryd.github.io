package com.example.fakenewsdetector.detection

interface NewsDetector {
	suspend fun analyze(text: String): DetectionResult
}