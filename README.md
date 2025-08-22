# FakeNewsDetector (Android, Jetpack Compose)

A minimal Android app scaffold with a simple heuristic-based fake news detection pipeline. Use this as a starting point to plug in a real model or remote API.

## Features
- Jetpack Compose UI (single-screen)
- `DetectorViewModel` with `StateFlow`
- `NewsDetector` interface and `HeuristicNewsDetector` implementation
- Material3 theme

## Getting Started
1. Open the project in Android Studio (Giraffe+ recommended).
2. Use a JDK 17 toolchain.
3. Let Gradle sync download dependencies.
4. Run on a device/emulator (minSdk 24).

If building from CLI:
```
./gradlew :app:assembleDebug
```

## How it Works
- Enter text and press Analyze.
- The `DetectorViewModel` calls `NewsDetector.analyze(text)`.
- The heuristic detector scores several red flags (clickbait phrases, punctuation, ALL CAPS, untrusted hints, numbers without citations) and returns a probability and reasons.

## Extending
- Replace `HeuristicNewsDetector` with a model-backed implementation:
  - On-device: TensorFlow Lite / ONNX Runtime
  - Remote: Call your API and map response to `DetectionResult`
- Add URL input and article fetching, then run analysis on extracted content.
- Persist history using Room or DataStore.

## Notes
- The heuristic is illustrative, not authoritative. Always validate with reliable sources.