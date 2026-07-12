// HomeScreen.kt — body-pace running companion dashboard
package com.bodypace.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bodypace.model.RunLog
import com.bodypace.model.WeatherSnapshot

@Composable
fun HomeScreen(
    weather: WeatherSnapshot,
    logs: List<RunLog>,
    runCount: Int,
    totalKm: Double,
    lastRun: RunLog?,
    onStartRun: () -> Unit
) {
    Scaffold {
        Column(
            Modifier.padding(it).padding(24.dp).verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))

            // Weather card
            WeatherCard(weather)

            Spacer(Modifier.height(16.dp))

            // Sedentary break card (placeholder)
            Surface(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFE3F2FD)
            ) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("💺", fontSize = 24.sp)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("久坐提醒", fontWeight = FontWeight.SemiBold)
                        Text("每小时站起来活动一下", color = Color.Gray, fontSize = 13.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Last run
            lastRun?.let { run ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🏃", fontSize = 18.sp)
                    Spacer(Modifier.width(6.dp))
                    Text("上次跑步", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.weight(1f))
                    Text(run.feelingSummary,
                        color = feelingColor(run.rpe), fontWeight = FontWeight.SemiBold)
                }
                Spacer(Modifier.height(10.dp))
                LastRunCard(run)
                Spacer(Modifier.height(24.dp))
            }

            // Body trend panel
            if (logs.size >= 2) {
                BodyTrendPanel(logs)
                Spacer(Modifier.height(24.dp))
            }

            // Cumulative stats
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📊", fontSize = 18.sp)
                Spacer(Modifier.width(6.dp))
                Text("累计", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth()) {
                StatBox("跑步次数", "$runCount", Modifier.weight(1f))
                Spacer(Modifier.width(12.dp))
                StatBox("总里程", "${"%.1f".format(totalKm)} km", Modifier.weight(1f))
            }

            Spacer(Modifier.height(32.dp))

            // CTA
            Button(
                onClick = onStartRun,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("开始跑步", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun WeatherCard(weather: WeatherSnapshot) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF1565C0)
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("${weather.tempCelsius}°C",
                        color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.W100)
                    Text(weather.condition, color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("湿度 ${weather.humidity}%", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                    Text("风速 ${weather.windKmh}km/h", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                }
            }
            HorizontalDivider(color = Color.White.copy(alpha = 0.15f), modifier = Modifier.padding(vertical = 12.dp))
            Row {
                Text("👕", fontSize = 18.sp)
                Spacer(Modifier.width(8.dp))
                Text(weather.gearSuggestion, color = Color.White, fontSize = 15.sp)
            }
        }
    }
}

@Composable
private fun LastRunCard(run: RunLog) {
    Surface(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.Gray.copy(alpha = 0.08f)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Metric("${run.distanceKm} km", "距离")
                Metric("${run.durationMinutes} min", "时长")
                Metric(run.pacePerKm, "配速")
                Metric("RPE $run.rpe", "强度")
            }
            if (run.bodySignals.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    run.bodySignals.take(5).forEach { signal ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.Gray.copy(alpha = 0.15f)
                        ) {
                            Text(signal, fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Metric(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(label, color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
private fun StatBox(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color.Gray.copy(alpha = 0.08f)
    ) {
        Column(Modifier.padding(vertical = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text(label, color = Color.Gray, fontSize = 13.sp)
        }
    }
}

@Composable
private fun BodyTrendPanel(logs: List<RunLog>) {
    val recent = recent4Weeks(logs)

    Surface(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.Gray.copy(alpha = 0.05f)
    ) {
        Column(Modifier.padding(20.dp)) {
            Row {
                Text("📈", fontSize = 16.sp)
                Spacer(Modifier.width(6.dp))
                Text("身体趋势", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(Modifier.height(20.dp))

            // RPE trend
            Text("疲劳度趋势", color = Color.Gray, fontSize = 13.sp)
            Spacer(Modifier.height(8.dp))
            Canvas(Modifier.fillMaxWidth().height(100.dp)) {
                val vals = recent.map { it.avgRpe }
                val maxRpe = vals.filter { it > 0 }.maxOrNull()?.coerceIn(5.0, 10.0) ?: 5.0
                val points = vals.mapIndexedNotNull { i, v ->
                    if (v == 0.0) null
                    else Offset(
                        size.width * 0.1f + size.width * 0.8f * i / (vals.size - 1).coerceAtLeast(1),
                        size.height * 0.1f + size.height * 0.8f * (1 - v / maxRpe).toFloat()
                    )
                }

                // Grid
                for (i in 0..4) {
                    val y = size.height * 0.1f + size.height * 0.8f * i / 4
                    drawLine(Color.Gray.copy(alpha = 0.2f),
                        Offset(size.width * 0.1f, y), Offset(size.width * 0.9f, y), 0.5f)
                }
                // Line + dots
                if (points.size >= 2) {
                    val path = androidx.compose.ui.graphics.Path().apply {
                        moveTo(points.first().x, points.first().y)
                        for (i in 1 until points.size) {
                            val cx = (points[i-1].x + points[i].x) / 2
                            cubicTo(cx, points[i-1].y, cx, points[i].y, points[i].x, points[i].y)
                        }
                    }
                    drawPath(path, Color(0xFF42A5F5), style = Stroke(2.5f, cap = StrokeCap.Round))
                }
                points.forEach { p ->
                    drawCircle(Color(0xFF1E88E5), 4f, p)
                }
            }

            Spacer(Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                recent.forEach { Text(it.weekLabel, color = Color.Gray, fontSize = 11.sp) }
            }

            Spacer(Modifier.height(20.dp))

            // Signal frequency
            Text("身体信号频率", color = Color.Gray, fontSize = 13.sp)
            Spacer(Modifier.height(8.dp))
            val signalCounts = mutableMapOf<String, Int>()
            recent.forEach { it.signals.forEach { s -> signalCounts[s] = (signalCounts[s] ?: 0) + 1 } }
            if (signalCounts.isEmpty()) {
                Text("还没有记录身体信号", color = Color.Gray, fontSize = 13.sp)
            } else {
                val sorted = signalCounts.entries.sortedByDescending { it.value }.take(6)
                val max = sorted.first().value.toFloat()
                sorted.forEach { (sig, count) ->
                    val isJoint = listOf("膝盖", "脚踝", "髋部", "足底", "小腿").contains(sig)
                    Row(Modifier.padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(sig, color = Color.DarkGray, fontSize = 13.sp,
                            modifier = Modifier.width(72.dp))
                        LinearProgressIndicator(
                            progress = { count / max },
                            modifier = Modifier.weight(1f).height(8.dp).clip(RoundedCornerShape(4.dp)),
                            color = if (isJoint) Color(0xFFEF5350) else Color(0xFF66BB6A),
                            trackColor = Color.Gray.copy(alpha = 0.1f)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("$count", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }

            // Warning
            val jointSignals = listOf("膝盖", "脚踝", "髋部", "足底", "小腿")
            var consecutive = 0
            for (b in recent.reversed()) {
                if (b.signals.any { jointSignals.contains(it) }) consecutive++ else break
            }
            if (consecutive >= 2) {
                Spacer(Modifier.height(12.dp))
                Surface(
                    Modifier.fillMaxWidth(),
                    color = Color(0xFFFFF3E0),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(Modifier.padding(12.dp)) {
                        Text("⚠️", fontSize = 16.sp)
                        Spacer(Modifier.width(8.dp))
                        Text("关节信号连续 $consecutive 周，建议减量或交叉训练",
                            color = Color(0xFFE65100), fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

// ── Helpers ──

private data class WeekBucket(val weekLabel: String) {
    val rpes = mutableListOf<Int>()
    val signals = mutableListOf<String>()
    val avgRpe: Double get() = if (rpes.isEmpty()) 0.0 else rpes.average()
}

@Composable
private fun recent4Weeks(logs: List<RunLog>): List<WeekBucket> {
    val buckets = mutableMapOf<Int, WeekBucket>()
    for (log in logs) {
        val key = (log.date.hashCode() % 4 + 1).coerceIn(1, 4) // simplified
        buckets.getOrPut(key) { WeekBucket("W$key") }.apply {
            rpes.add(log.rpe)
            signals.addAll(log.bodySignals)
        }
    }
    for (i in 1..4) buckets.getOrPut(i) { WeekBucket("W$i") }
    return buckets.values.sortedBy { it.weekLabel }
}

private fun feelingColor(rpe: Int): Color = when {
    rpe <= 3 -> Color(0xFF4CAF50)
    rpe <= 5 -> Color(0xFFEF6C00)
    rpe <= 7 -> Color(0xFFE64A19)
    else -> Color(0xFFD32F2F)
}
