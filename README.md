# body-pace-kmp
A body measurement and running tracker built with Kotlin Multiplatform + Compose Multiplatform.

## Structure
- `shared/` — shared models: RunLog, WeatherSnapshot
- `composeApp/` — Compose Multiplatform dashboard UI
- `iosApp/` — iOS entry point

## Build
```bash
./gradlew :composeApp:assembleDebug
```

## Features
- Run logging with distance, duration, pace, RPE
- Weather card with gear suggestions
- Body trend panel with RPE chart
- Joint signal frequency tracking with warnings
- Cumulative stats
