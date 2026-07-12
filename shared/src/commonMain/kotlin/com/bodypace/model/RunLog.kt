// RunLog.kt — body-pace run log and weather models
package com.bodypace.model

// val → SDP property (val/var map from final + field)
data class WeatherSnapshot(
    val tempCelsius: Int,
    val condition: String,
    val humidity: Int,
    val windKmh: Int,
    val gearSuggestion: String
)

data class RunLog(
    val date: Long, // epoch millis
    val durationMinutes: Int,
    val distanceKm: Double,
    val rpe: Int,
    val bodySignals: List<String>,
    val note: String? = null
) {
    // Enum → expression-body val
    val pacePerKm: String get() {
        if (distanceKm == 0.0) return "--"
        val pace = durationMinutes / distanceKm
        val min = pace.toInt()
        val sec = ((pace - min) * 60).roundToInt()
        return "$min'${sec.toString().padStart(2, '0')}\""
    }

    val feelingSummary: String get() = when {
        rpe <= 3 -> "轻松"
        rpe <= 5 -> "适中"
        rpe <= 7 -> "较累"
        else -> "极限"
    }
}

val bodySignalOptions = listOf(
    "膝盖", "脚踝", "髋部", "小腿", "足底",
    "呼吸顺畅", "呼吸急促", "精力充沛", "疲劳", "酸痛",
    "状态极佳", "不想跑但跑完了"
)
