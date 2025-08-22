package com.example.fakenewsdetector.detection

import kotlin.math.min

class HeuristicNewsDetector : NewsDetector {
	override suspend fun analyze(text: String): DetectionResult {
		val normalized = text.trim()
		if (normalized.isEmpty()) {
			return DetectionResult(
				fakeProbability = 0f,
				label = "No Input",
				reasons = listOf("Please enter some text to analyze.")
			)
		}

		val reasons = mutableListOf<String>()
		var score = 0.0
		var maxScore = 0.0

		// Heuristic 1: Excessive exclamation/question marks
		val punctCount = normalized.count { it == '!' || it == '?' }
		if (punctCount >= 3) {
			score += 1.0
			reasons += "Excessive sensational punctuation ($punctCount occurrences)."
		}
		maxScore += 1.0

		// Heuristic 2: Clickbait phrases
		val clickbaitPhrases = listOf(
			"you won't believe",
			"shocking",
			"breaks the internet",
			"what happens next",
			"exposed",
			"miracle cure",
			"secret the media won't tell",
			"must see"
		)
		val lower = normalized.lowercase()
		val matched = clickbaitPhrases.filter { lower.contains(it) }
		if (matched.isNotEmpty()) {
			score += min(1.0, matched.size * 0.5)
			reasons += "Clickbait phrasing detected: ${matched.joinToString()}"
		}
		maxScore += 1.0

		// Heuristic 3: ALL CAPS ratio
		val letters = normalized.filter { it.isLetter() }
		if (letters.isNotEmpty()) {
			val capsRatio = letters.count { it.isUpperCase() }.toDouble() / letters.length
			if (capsRatio > 0.5) {
				score += 1.0
				reasons += "Unusually high ALL-CAPS usage (${(capsRatio * 100).toInt()}%)."
			}
		}
		maxScore += 1.0

		// Heuristic 4: Source credibility hints (very naive)
		val untrustedHints = listOf(".blog", ".info", "click", "subscribe", "forward this")
		val untrustedHit = untrustedHints.any { lower.contains(it) }
		if (untrustedHit) {
			score += 0.8
			reasons += "Possible low-credibility source indicators present."
		}
		maxScore += 1.0

		// Heuristic 5: Numbers without sources
		val hasNumbers = normalized.any { it.isDigit() }
		val hasCitations = listOf("source:", "according to", "study", "research").any { lower.contains(it) }
		if (hasNumbers && !hasCitations) {
			score += 0.7
			reasons += "Contains numbers with no cited sources."
		}
		maxScore += 1.0

		val probability = if (maxScore > 0) (score / maxScore).toFloat() else 0f
		val label = if (probability >= 0.6f) "Likely Fake" else if (probability >= 0.35f) "Uncertain" else "Likely Real"

		return DetectionResult(
			fakeProbability = probability,
			label = label,
			reasons = if (reasons.isEmpty()) listOf("No obvious red flags detected.") else reasons
		)
	}
}