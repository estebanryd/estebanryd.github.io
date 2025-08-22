package com.example.fakenewsdetector.detection

data class DetectionResult(
	val fakeProbability: Float,
	val label: String,
	val reasons: List<String>
)